document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");

    if (token) {
        document.getElementById("btn-login").style.display = "none";
        document.getElementById("btn-cadastro").style.display = "none";
    } else {
        document.getElementById("btn-conta").style.display = "none";
    }
});

fetch('/components/menu-rigth.html')
  .then(res => res.text())
  .then(data => {
    const container = document.getElementById('menu-rigth-container');
    container.innerHTML = data;

    inicializarMenuHarburger();
  })
  .catch(err => console.error(err));

function inicializarMenuHarburger() {
  const menuHarburger = document.getElementById('menu-harburger');
  const menuRightContainer = document.getElementById('menu-rigth-container');

  menuHarburger.addEventListener('click', (e) => {
        e.stopPropagation();
        menuHarburger.classList.remove('menu-harburger');
        menuHarburger.classList.add('menu-harburger-remove');
        menuRightContainer.classList.remove('menu-rigth-container');
        menuRightContainer.classList.add('menu-rigth-container-open');
  });

  menuRightContainer.addEventListener('click', (e) => {
      e.stopPropagation();
  });


  document.body.addEventListener('click', () => {
    menuHarburger.classList.remove('menu-harburger-remove');
    menuHarburger.classList.add('menu-harburger');
    menuRightContainer.classList.add('menu-rigth-container');
    menuRightContainer.classList.remove('menu-rigth-container-open');
  });
}

