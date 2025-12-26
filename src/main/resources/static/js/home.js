document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");

    if(!token) {
        document.getElementById("btn-conta").style.display = "none";
    }

    if (token) {
        document.getElementById("btn-login").style.display = "none";
        document.getElementById("btn-cadastro").style.display = "none";
    }
});