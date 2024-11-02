
async function navigateTo(page) {
    const content_div = document.getElementById('content');
    const index_div = document.getElementsByTagName('html');

    try {
        const response = await fetch(`../${page}.html`);
    
        if(!response.ok) {
            throw new error("Page not found!");
        }

        const data = await response.text();

        if(page === "index") {
            index_div[0].innerHTML = data;            
        } else {
            content_div.innerHTML = data;
        }
    } catch (error) {
        content_div.innerHTML = `<h2>${error.message}!!!!</h2>`;
    }
}

navigateTo('index');