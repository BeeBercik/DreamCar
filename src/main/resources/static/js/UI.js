
class UI {
    static displayAllOffers(offers) {
        const content_div = document.getElementById('content');

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

    static async displayOfferDetails(offer) {
        document.getElementById('content').innerHTML = `
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
           
            <button class="add-to-favorites" id="toggleFavouriteBtn">Dodaj do ulubionych</button> 
        </div>`

        await ApiService.toggleFavouriteBtn(offer.id);
    }

    static updateUiForUser() {
        document.getElementById('user-button').style.display = 'none';
        document.getElementById('profile-button').style.display = 'block';
    }

    static updateUiForGuest() {
        document.getElementById('user-button').style.display = 'block';
        document.getElementById('profile-button').style.display = 'none';
    }

    static showIncorrectRegisterMessage(message) {
        const form_div_message = document.getElementById('register-form-message');
        form_div_message.classList.add('incorrect-data');
        form_div_message.innerHTML = message;
    }

    static showIncorrectLoginMessage(message) {
        const form_div_message = document.getElementById('login-form-message');
        form_div_message.classList.add('incorrect-data');
        form_div_message.innerHTML = message;
    }

    static showIncorrectOfferFormMessage(message) {
        const form_div_message = document.getElementById('offer-form-message');
        form_div_message.classList.add('incorrect-data');
        form_div_message.innerHTML = message;
    }

    static showSuccessfullRegisterMessage(message) {
        const form_div_message = document.getElementById('login-form-message');
        form_div_message.classList.add('successfull-register');
        form_div_message.innerHTML = message;
    }

    static generateUserProfile(user) {
        document.getElementById('user-login').innerHTML = user.login;
        document.getElementById('user-email').innerHTML = user.email;
        document.getElementById('user-phone').innerHTML = user.phone;
    }

    static loadUserOffers(offers) {
        const userOffersDiv = document.getElementById('user-offer-list');
        offers.forEach(offer => {
            const userOfferArticle = document.createElement('article');
            userOfferArticle.innerHTML = ` <a href="#" class="offer-link" onclick="showOfferDetails(${offer.id})">
                <article class="offer">
                <img src="/img/car.jpg" alt="Samochód 1" class="offer-img">
                <div class="offer-details">
                    <h4>${offer.title}</h4>
                    <p>Rocznik: ${offer.year}, Przebieg: ${offer.mileage} km</p>
                    <p class="price"><span>${offer.price}</span> PLN</p>
                </div>
                <div class="offer-actions">
                    <a href="#" class="edit-btn" onclick="initDisplayOfferToEdit(${offer.id})">Edytuj</a>
                    <a href="#" onclick="initDeleteOffer(${offer.id})" class="delete-btn">Usun</a>
                </div>
                </article>
            </a>`
            userOffersDiv.append(userOfferArticle);
        }); 
    }

    static generateUserOfferToEdit(offer) {
        document.getElementById('content').innerHTML = `
        <div class="edit-offer-container">
        <div id="offer-form-message"></div>
        <h2>Edytuj Ogłoszenie</h2>
        <form class="edit-offer-form" id="edit-offer-form">
            <label for="title">Tytuł</label>
            <input type="text" id="title" name="title" required value="${offer.title}"/>

            <label for="description">Opis</label>
            <textarea id="description" name="description" rows="4" required >${offer.description}</textarea>

            <label for="brand">Marka</label>
            <input type="text" id="brand" name="brand" required value="${offer.brand}"/>

            <div class="form-group">
            <label for="year">Rok produkcji:</label>
            <input type="number" id="year" name="year" value="${offer.year}">
            </div>

            <label for="mileage">Przebieg (km)</label>
            <input type="number" id="mileage" name="mileage" required value="${offer.mileage}"/>

            <label for="gearbox">Skrzynia biegów</label>
            <select id="gearbox" name="gearbox" required>
                <option value="1">Automatyczna</option>
                <option value="2">Manualna</option>
            </select>

            <label for="fuel">Rodzaj paliwa</label>
            <select id="fuel" name="fuel" required>
                <option value="1">Benzyna</option>
                <option value="2">Diesel</option>
                <option value="3">LPG</option>
                
            </select>

            <label for="price">Cena (PLN)</label>
            <input type="number" id="price" name="price" required value="${offer.price}"/>

            <button type="submit" onclick="initEditOffer(${offer.id}, event)">Zapisz zmiany</button>
        </form>
        </div>
`
        document.getElementById('fuel').value = offer.fuel.id;
        document.getElementById('gearbox').value = offer.gearbox.id;
   }

   static displayNotLoggedInMessagesInFavourites() {
        document.getElementById('content').innerHTML = `<div class="not-logged-in-container">
                <h2>Nie jesteś zalogowany</h2>
                <p>Aby dodać oferty do ulubionych, musisz być zalogowany.</p>
                <a href="#" class="login-btn" onclick="navigateTo('login')">Zaloguj się</a>
            </div>`;
   }

   static displayLackOfFavouriteOffersMessage() {
        document.getElementById('content').innerHTML = `<div class="favourites-container" id="favourites-container">
        <h2>Ulubione Oferty</h2>
        <div class="no-favourites-message">
        <p>You dont have any favourite offer yet.</p>
        </div></div>`;
   }

   static displayFavourites(offers) {
        offers.forEach(offer => {
            const favOfferArticle = document.createElement('article');
            favOfferArticle.classList.add("favourite-offer");

            favOfferArticle.innerHTML = `
            <img src="/img/car.jpg" class="favourite-offer-img">
            <div class="favourite-offer-details">
                <h4>${offer.title}</h4>
                <p>Rocznik: ${offer.year}, Przebieg: ${offer.mileage} km</p>
                <p class="price"><span>${offer.price}</span> PLN</p>
            </div>
            <button class="remove-favourite-btn" onclick="removeFromFavourites(${offer.id})>Usuń</button>`

            document.getElementById('user-favourite-list').appendChild(favOfferArticle);
        });
    }

    static updateToggleFavouriteBtn(id, result) {
        const favouriteToggleBtn = document.getElementById('toggleFavouriteBtn');

        if(result) {
            favouriteToggleBtn.className = "remove-from-favorites";
            favouriteToggleBtn.innerHTML = 'Usun z ulubionych';
            favouriteToggleBtn.onclick = () => initRemoveFromFavourites(id);
        } else {
            favouriteToggleBtn.className = "add-to-favorites";
            favouriteToggleBtn.innerHTML = 'Dodaj do ulubionych';
            favouriteToggleBtn.onclick = () => initAddToFavourites(id);
        }
    }
}