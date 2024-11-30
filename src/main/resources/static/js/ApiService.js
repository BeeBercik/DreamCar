
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
        const response = await fetch("/api/registerUser", {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(userData)
        });

        const message = await response.text(); 
        if(response.ok) navigateTo('login', true, message);
        else UI.showIncorrectRegisterMessage(message);
    }

    static async loginUser(userData) {
        const response = await fetch('/api/loginUser', {
            method: 'POST',
            headers: {'Content-type': 'application/JSON'},
            body: JSON.stringify(userData)
        });
        
        const message = await response.text();
        if(response.ok) navigateTo('index', true, message);
        else UI.showIncorrectLoginMessage(message);
    }

    static async logoutUser() {
        const response = await fetch('/api/logoutUser');
        if(!response.ok) throw new Error(await response.text());
        navigateTo('index', true, await response.text());
    }

    static async getLoggedUser() {
        const response = await fetch("/api/getLoggedUser");
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
            navigateTo('user-profile', true, await response.text());
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
            navigateTo('user-profile', true, await response.text());
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

        const message = await response.text();
        if(!response.ok) throw new Error(message);
        UI.showMessageUnderTheHeader(true, message);
    }

    static async getFavourites() {
        const response = await fetch('/api/getFavouriteUserOffers');

        if(response.ok) {
            const favouriteOffers = await response.json();
            UI.displayFavourites(favouriteOffers);
        } else if(response.status === 401)
            UI.displayNotLoggedInMessagesInFavourites();
    }

    static async addToFavourites(id) {
        const response = await fetch("/api/addToFavourites/" + id);

        const message = await response.text();
        if(!response.ok) UI.showMessageUnderTheHeader(false, message);
        else {
            UI.showMessageUnderTheHeader(true, message)
            UI.updateToggleFavouriteBtn(id, true);
        }
    }

    static async removeFromFavourites(id) {
        const response = await fetch("/api/removeFromFavourites/" + id);

        const message = await response.text();
        if(!response.ok) throw new Error(message);
        UI.showMessageUnderTheHeader(true, message)
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