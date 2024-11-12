navigateTo('index');

async function navigateTo(page) {
    const content_div = document.getElementById('content');
    const index_div = document.getElementsByTagName('html');

    try {
        const response = await fetch(`../${page}.html`);

        if(!response.ok) throw new error("Page not found!");

        const data = await response.text();

        if(page === "index") {
            index_div[0].innerHTML = data;
            loadAllOffers();
        } else {
            content_div.innerHTML = data;
        }
    } catch (error) {
        content_div.innerHTML = `<h2>${error.message}!</h2>`;
    }
}

async function loadAllOffers() {
    try {
        const offers = await ApiService.getAllOffers();
        UI.displayAllOffers(offers);
    } catch(error) {
        document.getElementById("content").innerHTML = error.message;
    }
}

async function showOfferDetails(id) {
    try {
        const offer = await ApiService.getOfferDetails(id);
        UI.displayOfferDetails(offer);
    } catch(error) {
        document.getElementById('content').innerHTML = error.message;
    }
}

document.getElementById("register-form").addEventListener('submit', async function initUserRegister(event) {
    event.preventDefault();
    try {
        const login = document.getElementById('login').value;
        const mail = document.getElementById('mail').value;
        const password = document.getElementById('password').value;
        const rep_password = document.getElementById('rep-password').value
        const phone = document.getElementById('phone').value;

        const userData = {login, mail, password, rep_password, phone};
        
        await ApiService.registerUser(userData); 
    } catch(error) {
        document.getElementById('content').innerHTML = error.message;
    }
});