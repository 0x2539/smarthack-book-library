# http://colorbrewer2.org/#type=qualitative&scheme=Set3&n=12
# http://tools.medialab.sciences-po.fr/iwanthue/
# Warning: Make sure there are enough colors for every cluster
COLORS = [
  '#6fd159'
  '#964ed2'
  '#d6d534'
  '#513780'
  '#c38742'
  '#7a91c4'
  '#c3473d'
  '#83ccb4'
  '#ca5498'
  '#526c3f'
  '#cba0a4'
  '#503339'
]

$ ->
  $.getJSON 'api/chart', (data) ->
    load2Dfigure 'figure-2D', data
    load3Dfigure 'figure-3D', data


# utils
@transpose = (data) ->
  """
  transpose(transpose(x)) is x
  >>> transpose [
    {n: 1, l: 'a'},
    {n: 2, l: 'b'}
  ]
  {
    n: [1, 2],
    l: ['a', 'b']
  }

  >>> transpose {
    n: [1, 2],
    l: ['a', 'b']
  }
  [
    {n: 1, l: 'a'},
    {n: 2, l: 'b'}
  ]
  """
  # https://www.npmjs.com/package/object-transpose
  transposeArray = (arr) ->
    builtObj = {}
    for obj, i in arr
      for key, value of obj
        builtObj[key] ?= []
        builtObj[key][i] = value
    builtObj

  transposeObject = (obj) ->
    builtArr = []
    for key, arr of obj
      for value, i in arr
        builtArr[i] ?= {}
        builtArr[i][key] = value if value?
    builtArr

  return transposeArray(data) if Array.isArray(data)
  return transposeObject(data)

@letterNumber = (n) ->
  """
  >>> letterNumber 0
  A
  >>> letterNumber 1
  B
  """
  n = +n  # make sure it's a number
  codeOfFirst = 'A'.charCodeAt 0
  String.fromCharCode(codeOfFirst + n)




hideUnneeded = ->
  """
  Some elements do not have an option to be hidden,
  so we just hide their DOM element after plot creation
  """
  $('.modebar-btn--logo').hide()  # Plotly logo after the mode bar
  $('g.pointtext').hide()  # Little text above cluster icons in the legend

load2Dfigure = (containerId, coords) ->
  config =
    modeBarButtonsToRemove: [
      'hoverClosestCartesian'
      'hoverCompareCartesian'
      'sendDataToCloud'
      'autoScale2d'
      'zoomIn2d'
      'zoomOut2d'
    ]
    scrollZoom: on


  layout =
    title: 'chart'
    hovermode: 'closest'
    dragmode: 'pan'

    xaxis:
      showticklabels: no,
      zeroline: off

    yaxis:
        showticklabels: no,
        zeroline: off

    height: 600
    margin:
      t: 50
      b: 0
      l: 0
      r: 0

  # Plotly colors based on the group eg:
  # data = [ group1: {x: [...], y: [...]}, group2: {x: [...], y: [...]} ]
  grouped = _.groupBy transpose(coords), 'cluster2D'

  data = []
  for clusterNr, transposedEntries of grouped
    entries = transpose transposedEntries

    data.push
      name: letterNumber clusterNr
      type: 'scatter'
      mode: 'markers+text'
      hoverinfo: 'text'
      textposition: 'top center'
#      textfont: size: 10

      marker:
        color: COLORS[clusterNr]
#        sizeref: .48
#        size:    entries.freq

      text: entries.book
      x:    entries.x2D
      y:    entries.y2D

  Plotly.newPlot containerId, data, layout, config
  hideUnneeded()


load3Dfigure = (containerId, coords) ->
  config =
    modeBarButtonsToRemove: [
      'sendDataToCloud'
      'tableRotation'
      'resetCameraLastSave3d'
      'hoverClosest3d'
    ]

  layout =
    title: 'Action Similarities (3D)'
    hovermode: 'closest'

    scene:
      xaxis:
        showticklabels: no
        showspikes: no
        zeroline: off
        title: ''
      yaxis:
        showticklabels: no
        showspikes: no
        zeroline: off
        title: ''
      zaxis:
        showticklabels: no
        showspikes: no
        zeroline: off
        title: ''

#    height: 600
    margin:
      t: 50
      b: 0
      l: 0
      r: 0

  grouped = _.groupBy transpose(coords), 'cluster3D'

  data = []
  for clusterNr, transposedEntries of grouped
    entries = transpose transposedEntries

    data.push
      name: letterNumber clusterNr
      type: 'scatter3d'
      mode: 'markers+text'
      hoverinfo: 'text'
      textposition: 'top center'
#      textfont: size: 10
#      marker:
#        sizeref: 0.48
#        size: entries.freq
      text: entries.book
      x: entries.x3D
      y: entries.y3D
      z: entries.z3D

  Plotly.newPlot containerId, data, layout, config
  hideUnneeded()
