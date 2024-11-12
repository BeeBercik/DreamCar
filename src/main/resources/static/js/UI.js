
class UI {
    static displayAllOffers(offers) {
        const content_div = document.getElementById('content');
        content_div.innerHTML = '';

        offers.forEach(offer => {
            const offerArticle = document.createElement('article');
            offerArticle.classList.add('offer');
            offerArticle.innerHTML = `
                <a href="#" onclick="showOfferDetails(${offer.id})">
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

    static displayOfferDetails(offer) {
        const content_div = document.getElementById('content');
        content_div.innerHTML = '';

        content_div.innerHTML = `
        <div class="offer-details-container">
            <div class="offer-content">
                <div class="offer-image">
                    <img src="/img/car.jpg" alt="Audi A4 2.0 TDI" class="offer-img">
                </div>
                <div class="offer-info">
                    <h1>${offer.title}</h1>
                    <p class="price">${offer.price} PLN</p>
                    <div class="offer-details">
                        <p><strong>Marka: </strong> ${offer.brand}</p>
                        <p><strong>Rocznik: </strong>${offer.year}</p>
                        <p><strong>Przebieg: </strong>${offer.mileage} km</p>
                        <p><strong>Skrzynia biegów: </strong>${offer.gearbox.name}</p>
                        <p><strong>Rodzaj paliwa: </strong>${offer.fuel.name}</p>
                    </div>
                </div>
            </div>

            <div class="separator"></div>

            <div class="offer-description">
                <h2>Opis</h2>
                <p>${offer.description} </p>
            </div>
            <div class="offer-seller">
                <h3>Sprzedawca:</h3>
                <p><strong>${offer.user.login}</strong></p>
                <p>e-mail: ${offer.user.email}</p>
                <p>telefon: ${offer.user.phone}</p>
            </div>
            <button class="add-to-favorites">Dodaj do ulubionych</button>
            <p class="offer-date">Dodano: <span>${offer.add_date}</span></p>
        </div>`
    }

    static displayRegisterMessage(status) {
        
    }
}