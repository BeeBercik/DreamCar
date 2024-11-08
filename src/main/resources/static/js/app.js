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
