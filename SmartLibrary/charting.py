from sklearn.decomposition import RandomizedPCA
from sklearn.cluster import AffinityPropagation
import pandas as pd

def project():
    w2v = Word2Vec.load(OUTPUT_FOLDER + '/' + W2V_FILE)
    df = pd.DataFrame(index=w2v.index2word)
    df.index.name = 'action'
    X = [w2v[action] for action in df.index]

    if PCA_FIRST:
        pca = RandomizedPCA(n_components=50)
        vprint('Running PCA')
        X = pca.fit_transform(X)

    dim_names = 'xyz'
    for dim in [2, 3]:
        tsne = TSNE(n_components=dim, learning_rate=TSNE_LR, perplexity=10, n_iter=2500)
        vprint('Running TSNE %dD' % dim)
        transformed = tsne.fit_transform(X)

        for i, name in enumerate(dim_names[:dim]):
            df['{}{}D'.format(name, dim)] = [coord[i] for coord in transformed]

    df.to_csv(OUTPUT_FOLDER + '/' + COORDS_FILE)


def scale():
    df = pd.read_csv(OUTPUT_FOLDER + '/' + COORDS_FILE).set_index('action')

    stories = read_stories().replace('\n', ' ').split(' ')
    for [action, count] in itemfreq(stories):
        if action in df.index:
            df.loc[action, 'freq'] = int(count)

    # Scale frequencies to a [min, max] range (for UI sizing)
    df.freq = MIN_SIZE + ((df.freq - df.freq.min()) / df.freq.max()) * (MAX_SIZE - MIN_SIZE)

    df.to_csv(OUTPUT_FOLDER + '/' + COORDS_FILE)


def cluster():
    df = pd.read_csv(OUTPUT_FOLDER + '/' + COORDS_FILE).set_index('action')
    for dim in [2, 3]:
        dim_cols = [col for col in df.columns if col.endswith(str(2) + 'D')]
        cl = AffinityPropagation(max_iter=500)
        vprint('Running Clustering %dD' % dim)
        labels = cl.fit_predict(df[dim_cols])
        df['cluster%dD' % dim] = labels

    df.to_csv(OUTPUT_FOLDER + '/' + COORDS_FILE)
