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

function loadAllOffers() {
    const content_div = document.getElementById('content');
    content_div.innerHTML = '';

    fetch("/allOffers").then(response => {
            if(response.ok) return response.json();
            throw new Error("blad przy ladowaniu ofert");
        }).then(offers => {
            displayOffers(offers);
    }).catch(error => {
        content_div.innerHTML = error.message;
    });
}

function displayOffers(offers) {
    const content_div = document.getElementById('content');

    offers.forEach(offer => {
        const offerArticle = document.createElement('article');
        offerArticle.classList.add('offer');
        offerArticle.innerHTML = `
         <a href="#" onclick="navigateTo('offer-details')">
             <div class="img">
                 <img src="/img/car.jpg" alt="">
             </div>
             <div class="details">
                 <h2>${offer.title}</h2>
                 <ul>
                    <li>${offer.gearbox.name}</li>
                    <li>${offer.fuel.name}</li>
                    <li>${offer.mileage}</li>
                    <li>${offer.year}</li>
                 </ul>
                 <p class="price"><span>${offer.price}</span>pln</p>
             </div>
         </a>`;
         content_div.appendChild(offerArticle);
     });
}


