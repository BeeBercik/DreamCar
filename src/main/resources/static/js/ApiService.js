
class ApiService {
    static async getAllOffers() {
        const response = await fetch("/api/allOffers");
        if(!response.ok) throw new Error("Some problems with loading offers.."); 
        
        return await response.json();
    }

    static async getOfferDetails(id) {
        const response = await fetch("/api/offerDetails/" + id);
        if(!response.ok) throw new Error("Some problems with offer details..");
        
        return await response.json();;
    }

    static async registerUser(userData) {
        const respone = await fetch("/api/registerUser", {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(userData)
        });

        const message = await respone.text(); 
        if(respone.ok) navigateTo('login', true, message);
        else UI.showIncorrectRegisterMessage(message);
    }

    static async loginUser(userData) {
        const response = await fetch('/api/loginUser', {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(userData)
        });

        if(response.ok) navigateTo('index');
        else {
            const message = await response.text();
            UI.showIncorrectLoginMessage(message);
        }
    }

    static async logoutUser() {
        const response = await fetch('/api/logoutUser');
        if(!response.ok) throw new Error(await response.text());
        navigateTo('index');
    }

    static async checkIfUserLoggedIn() {
        const response = await fetch("/api/isUserLoggedIn");
        if(response.ok) {
            UI.updateUiForUser(); 
            return await response.json();
        } else UI.updateUiForGuest();
    }

    static async getUserOffers() {
        const response = await fetch("/api/getUserOffers");
        if(!response.ok) throw new Error(await response.text()); 
        const offers = await response.json();
        UI.loadUserOffers(offers);
    }

    static async addNewOffer(offerData) {
        const response = await fetch('/api/addNewOffer', {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(offerData)
        });

        if(response.ok)  {
            alert('Dodano oferte!');
            navigateTo('user-profile');
        } else if(response.status === 401 ||
                    response.status === 404) { 
            throw new Error(await response.text()); 
        } else UI.showIncorrectOfferFormMessage(await response.text());
    }

    static async editUserOffer(id, offerData) {
        const response = await fetch("/api/editUserOffer/" + id, {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(offerData)
        });

        if(response.ok) {
            alert('Pomyslnie dodano oferte.');
            navigateTo('user-profile');
        } else if(response.status === 403 ||
                    response.status === 404) {
            throw new Error(await response.text());
        } else UI.showIncorrectOfferFormMessage(await response.text());   
    }

    static async displayOfferToEdit(id) {
        const response = await fetch("/api/getOffer/" + id);
        if(!response.ok) throw new Error(await response.text());

        UI.generateUserOfferToEdit(await response.json());
    }

    static async deleteOffer(id) {
        const response = await fetch("/api/deleteOffer/" + id);
        console.log(response);
        if(!response.ok) throw new Error(await response.text());
        else {
            alert('Pomyslnie usunieto oferte.');
            navigateTo('user-profile');
        }
    }

    static async getFavourites() {
        const response = await fetch('/api/getFavouriteUserOffers');

        if(response.ok) 
            UI.displayFavourites(await response.json());
        else if(response.status === 401)
            UI.displayNotLoggedInMessagesInFavourites();
        else if((await response.json().length === 0))
            UI.displayLackOfFavouriteOffersMessage();
    }

    static async addToFavourites(id) {
        const response = await fetch("/api/addToFavourites/" + id);

        if(!response.ok) throw new Error(await response.text())
        alert("Oferta dodana do ulubionych");
        UI.updateToggleFavouriteBtn(id, true);
    }

    static async removeFromFavourites(id) {
        const response = await fetch("/api/removeFromFavourites/" + id);

        if(!response.ok) throw new Error(await response.text());
        alert("Oferta usunieta do ulubionych");
        UI.updateToggleFavouriteBtn(id, false);
    }

    static async isOfferInFavourites(id) {
        const response = await fetch("/api/isOfferInFavourites/" + id);

        if(response.ok) return true;
        else return false;
    }

    static async toggleFavouriteBtn(id) {
        const result = await ApiService.isOfferInFavourites(id);
        UI.updateToggleFavouriteBtn(id, result);
    }
}