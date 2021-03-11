!(function () {
  const font = [
    ['███╗   ██╗ ██████╗ ██╗███╗   ██╗██╗  ██╗    ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗███████╗██████╗ '],
    ['████╗  ██║██╔════╝ ██║████╗  ██║╚██╗██╔╝    ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝╚══███╔╝██╔════╝██╔══██╗'],
    ['██╔██╗ ██║██║  ███╗██║██╔██╗ ██║ ╚███╔╝     ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝   ███╔╝ █████╗  ██████╔╝'],
    ['██║╚██╗██║██║   ██║██║██║╚██╗██║ ██╔██╗     ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝   ███╔╝  ██╔══╝  ██╔══██╗'],
    ['██║ ╚████║╚██████╔╝██║██║ ╚████║██╔╝ ██╗    ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████╗███████╗██║  ██║'],
    ['╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝    ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚══════╝╚═╝  ╚═╝'],
    [],
    ['                                                                                                                            Designed by KA']
  ]
  const encode = btoa(encodeURI(JSON.stringify(font)))
  if (!(window.ActiveXObject || 'ActiveXObject' in window)) {
    const log = ['%c']
    const obj = JSON.parse(decodeURI(atob(encode)))
    obj.forEach(function(data) {
      const arr = data[0]
      log.push(arr)
    })
    const str = [log.join('\n')].concat(['color:#00bda3'])
    console.log.apply(console, str)
  }

  const author = document.createElement('div')
  author.innerText = 'Designed by KA'
  author.style.color = '#00bda3'
  author.style.position = 'fixed'
  author.style.right = '25px'
  author.style.bottom = '15px'
  document.body.append(author)
}())
