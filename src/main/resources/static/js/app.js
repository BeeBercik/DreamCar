ApiService.checkIfUserLoggedIn();
loadAllOffers();

window.addEventListener('DOMContentLoaded', ApiService.checkIfUserLoggedIn);

async function navigateTo(page, registered = false, message = null) {
    ApiService.checkIfUserLoggedIn();
    const content_div = document.getElementById('content');

    try {
        const response = await fetch(`../${page}.html`);
        if(!response.ok) throw new Error("Page not found!");
        const data = await response.text();

        content_div.innerHTML = '';
        if(page !== 'index') content_div.innerHTML = data;
        switch(page) {
            case 'index':
                loadAllOffers();
                break;
            case 'register':
                document.getElementById("register-form").addEventListener('submit', initUserRegister);
                break;
            case 'login':
                document.getElementById('login-form').addEventListener('submit', initUserLogin);

                if(registered) UI.showSuccessfullRegisterMessage(message);
                break;
            case 'user-profile':
                const user = await ApiService.getLoggedUser();
                UI.generateUserProfile(user);
                await ApiService.getUserOffers();
                break;
            case 'new-offer':
                document.getElementById('new-offer-form').addEventListener('submit', initAddNewOffer);
                break;
            case 'edit-offer':
                document.getElementById('new-offer-form').addEventListener('submit', initEditOffer);
            default: 
                break;
        }
    } catch (error) {
        content_div.innerHTML = `<h2>${error.message}!</h2>`;
        console.log(error);
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
        document.getElementById('content').innerHTML = error.message;
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
        document.getElementById('content').innerHTML = error.message;
    }
}

async function logoutUser() {
    try {
        await ApiService.logoutUser();
    } catch(error) {
        document.getElementById('content').innerHTML = error.message;
    }
}

async function initAddNewOffer(event) {
    event.preventDefault();
    try {
        const user = await ApiService.getLoggedUser();

        await ApiService.addNewOffer(getOfferData(user));
    } catch(error) {
        document.getElementById('content').innerHTML = error.message;
    }
}

async function initEditOffer(offerId, event) {
    event.preventDefault();
    try {
        const user = await ApiService.getLoggedUser();

        await ApiService.editUserOffer(offerId, getOfferData(user));
    } catch (error) {
        document.getElementById('content').innerHTML = error.message;
        console.log(error);
    }
}

async function initDisplayOfferToEdit(id) {
    try {
        const offer = await ApiService.getOffer(id);
        
        UI.generateUserOfferToEdit(offer);
    } catch (error) {
        document.getElementById('content').innerHTML = error.message;
    }
}

async function initDeleteOffer(id) {
    try {
        ApiService.getLoggedUser();
        await ApiService.deleteOffer(id);
    } catch(error) {
        document.getElementById('content').innerHTML = error.message;
        console.log(error);
    }
}

function getOfferData(user) {
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
        user: user.id,
        gearbox: gearbox,
        fuel: fuel
    }
    
    return offerData;
}