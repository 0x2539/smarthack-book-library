from sklearn.decomposition import PCA
from sklearn.cluster import AffinityPropagation
import pandas as pd

from .models import Book

CACHED_COORDS = None


def compute_coords():
    global CACHED_COORDS
    if CACHED_COORDS is not None:
        return CACHED_COORDS

    df = pd.DataFrame.from_records(Book.objects.all().values())
    df = df[['short_name', 'humor', 'id', 'nr_pages', 'popularity']]
    df = df.set_index('short_name')
    df.index.name = 'book'

    X = df.values

    coords = pd.DataFrame(index=df.index)

    dim_names = 'xyz'
    for dim in [2, 3]:
        # tsne = TSNE(n_components=dim, learning_rate=TSNE_LR, perplexity=10, n_iter=2500)
        # print('Running TSNE %dD' % dim)
        # X = pca.fit_transform(X)
        # transformed = tsne.fit_transform(X)

        pca = PCA(n_components=dim)
        print('Running PCA %dD' % dim)
        transformed = pca.fit_transform(X)

        for i, name in enumerate(dim_names[:dim]):
            coords['{}{}D'.format(name, dim)] = [coord[i] for coord in transformed]

        dim_cols = [col for col in coords.columns if col.endswith(str(2) + 'D')]
        cl = AffinityPropagation(max_iter=500)
        print('Running Clustering %dD' % dim)
        labels = cl.fit_predict(coords[dim_cols])
        coords['cluster%dD' % dim] = labels

    coords['book'] = coords.index
    CACHED_COORDS = coords
    return coords


# def scale():
#     df = pd.read_csv(OUTPUT_FOLDER + '/' + COORDS_FILE).set_index('action')
#
#     stories = read_stories().replace('\n', ' ').split(' ')
#     for [action, count] in itemfreq(stories):
#         if action in df.index:
#             df.loc[action, 'freq'] = int(count)
#
#     # Scale frequencies to a [min, max] range (for UI sizing)
#     df.freq = MIN_SIZE + ((df.freq - df.freq.min()) / df.freq.max()) * (MAX_SIZE - MIN_SIZE)
#
#     df.to_csv(OUTPUT_FOLDER + '/' + COORDS_FILE)
