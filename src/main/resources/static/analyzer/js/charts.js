function pieOption(title, data) {
  let list = []
  for (let key in data) {
    let map = {}
    map.name = key
    map.value = data[key]
    list.push(map)
  }

  return {
    title: {
      text: title,
      left: 'left'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 10,
      data: title
    },
    series: [
      {
        name: title,
        type: 'pie',
        radius: '50%',
        data: list,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
}

function barOption(title, data) {
  let keys = []
  let vals = []

  const sortable = Object.fromEntries(
    Object.entries(data).sort(([a,],[b,]) => a-b)
  ) // ES 10 排序

  for (let key in sortable) {
    vals.push(data[key])
    if (/^\d+$/.test(key)) {
      const date = new Date(parseInt(key))
      key = date.getFullYear() +
        '-' + (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) +
        '-' + (date.getDate() < 10 ? '0'+date.getDate() : date.getDate())
    }
    keys.push(key)
  }

  return {
    title: {
      text: title,
      left: 10
    },
    toolbox: {
      feature: {
        dataZoom: {
          yAxisIndex: false
        }
      }
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      bottom: 90
    },
    dataZoom: [{
      type: 'inside'
    }, {
      type: 'slider'
    }],
    xAxis: {
      data: keys,
      silent: false,
      splitLine: {
        show: false
      },
      splitArea: {
        show: false
      }
    },
    yAxis: {
      splitArea: {
        show: false
      }
    },
    series: [
      {
        type: 'bar',
        large: true,
        data: vals,
      }
    ]
  }
}

function winShow(chartsUrl, detailsUrl) {
  const win = document.getElementsByClassName('win')
  win[0].style.display = 'table'
  const charts = document.getElementsByClassName('charts')
  charts[0].setAttribute('href', chartsUrl)
  const details = document.getElementsByClassName('details')
  details[0].setAttribute('href', detailsUrl)
}

function winHide() {
  const win = document.getElementsByClassName('win')
  win[0].style.display = 'none'
  const charts = document.getElementsByClassName('charts')
  charts[0].setAttribute('href', '')
  const details = document.getElementsByClassName('details')
  details[0].setAttribute('href', '')
}

function loadingHide() {
  const loading = document.getElementsByClassName('loading')
  loading[0].style.display = 'none'
}
