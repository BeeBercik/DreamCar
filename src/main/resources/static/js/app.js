ApiService.getLoggedUser();
loadAllOffers();

window.addEventListener('DOMContentLoaded', ApiService.getLoggedUser);

async function navigateTo(page, flag = false, message = '') {
    try {
        const user = await ApiService.getLoggedUser();
        const content_div = document.getElementById('content');
        
        const response = await fetch(`../${page}.html`);
        if(!response.ok) throw new Error("Page not found!");
        const data = await response.text();

        content_div.innerHTML = '';
        if(page !== 'index') content_div.innerHTML = data;
        switch(page) {
            case 'index':
                if(flag) UI.showMessageUnderTheHeader(true, message);
                loadAllOffers();
                break;
            case 'register':
                document.getElementById("register-form").addEventListener('submit', initUserRegister);
                break;
            case 'login':
                if(flag) UI.showMessageUnderTheHeader(true, message);
                document.getElementById('login-form').addEventListener('submit', initUserLogin);
                break;
            case 'user-profile':
                if(flag) UI.showMessageUnderTheHeader(true, message);
                UI.generateUserProfile(user);
                await ApiService.getUserOffers();
                break;
            case 'new-offer':
                document.getElementById('new-offer-form').addEventListener('submit', initAddNewOffer);
                break;
            case 'edit-offer':
                document.getElementById('edit-offer-form').addEventListener('submit', initEditOffer);
                break;
            case 'favourites':
                initLoadFavourites();
                break;
            default: 
                break;
        }
    } catch (error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function loadAllOffers() {
    try {
        const offers = await ApiService.getAllOffers();
        UI.displayAllOffers(offers);
    } catch(error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function showOfferDetails(id) {
    try {
        const offer = await ApiService.getOfferDetails(id);
        UI.displayOfferDetails(offer);
    } catch(error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function initUserRegister(event) {
    event.preventDefault();
    try {
        const login = document.getElementById('login').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const rep_password = document.getElementById('rep_password').value
        const phone = document.getElementById('phone').value;

        const userData = {
            login: login,
            email: email,
            password: password,
            rep_password: rep_password,
            phone: phone
        };
        await ApiService.registerUser(userData); 
    } catch(error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function initUserLogin(event) {
    event.preventDefault();
    try {
        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;
        
        const userData = {
            login: login,
            password: password
        }
        await ApiService.loginUser(userData);
    } catch (error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function logoutUser() {
    try {
        await ApiService.logoutUser();
    } catch(error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function initAddNewOffer(event) {
    event.preventDefault();
    try {
        await ApiService.addNewOffer(getOfferData());
    } catch(error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function initEditOffer(offerId, event) {
    event.preventDefault();
    try {
        await ApiService.editUserOffer(offerId, getOfferData());
    } catch (error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function initDisplayOfferToEdit(id) {
    try {
        const offer = await ApiService.displayOfferToEdit(id);
    } catch (error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function initDeleteOffer(id) {
    try {
        await ApiService.deleteOffer(id);
    } catch(error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

function getOfferData() {
    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;
    const brand = document.getElementById('brand').value;
    const mileage = document.getElementById('mileage').value;
    const gearbox = document.getElementById('gearbox').value;
    const fuel = document.getElementById('fuel').value;
    const year = document.getElementById('year').value;
    const price = document.getElementById('price').value;

    const offerData = {
        title: title,
        description: description,
        brand: brand,
        mileage: mileage,
        year: year,
        price: price,
        gearbox: gearbox,
        fuel: fuel
    }
    
    return offerData;
}

async function initLoadFavourites() {
    try {
        await ApiService.getFavourites();
    } catch(error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function initAddToFavourites(id) {
    try {
        await ApiService.addToFavourites(id);
    } catch(error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}

async function initRemoveFromFavourites(id) {
    try {
        await ApiService.removeFromFavourites(id);
    } catch(error) {
        UI.displayCriticalAppError();
        console.log(error);
    }
}
