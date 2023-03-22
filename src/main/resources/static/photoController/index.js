console.log("Index: JS OK!");

const photoListContainer = document.querySelector('#photo-list');
const categorySelect = document.querySelector('#category-select');
const searchInput = document.querySelector('#search-input');

axios.get('http://localhost:8080/api/photos')
	.then((res) => {
		const photosData = res.data.photos;
		const categories = res.data.categories;

		insertCategoriesIntoSelect(categories);

		// TODO : il controller già dispone dei filtri, basta aggiungere "categoryId=1&title=my%20title" nella chiamata, %20 per gli spazi vuoti
		// ma è anche vero che cosi evitamo di fare nuovamente una chiamata nascondendo solo gli elementi
		categorySelect.addEventListener('change', () => {
			filterPhotosByCategory(photosData, categorySelect.value);
		});

		searchInput.addEventListener('input', () => {
			filterPhotosBySearchInput(photosData, searchInput.value.toLowerCase());
		});

		insertPhotosIntoContainer(photosData);
	})
	.catch((err) => {
		console.error('Errore nella richiesta', err);
		alert('Errore durante la richiesta!');
	});

function insertCategoriesIntoSelect(categories) {
	categories.forEach(category => {
		const option = `<option value="${category.name}">${category.name}</option>`;
		categorySelect.insertAdjacentHTML('beforeend', option);
	});
}

function filterPhotosByCategory(photosData, category) {
	photosData.forEach(photo => {
		const photoElement = document.querySelector(`#photo-${photo.id}`);
		if (category === '' || photo.categories.some(cat => cat.name === category)) {
			photoElement.classList.remove('d-none');
		} else {
			photoElement.classList.add('d-none');
		}
	});
}

function filterPhotosBySearchInput(photosData, filterValue) {
	photosData.forEach(photo => {
		const photoElement = document.querySelector(`#photo-${photo.id}`);
		const title = photo.title.toLowerCase();
		const tags = photo.tag.toLowerCase();
		if (title.includes(filterValue) || tags.includes(filterValue)) {
			photoElement.classList.remove('dis-none');
		} else {
			photoElement.classList.add('dis-none');
		}
	});
}

function insertPhotosIntoContainer(photosData) {
	photosData.forEach(photo => {
		const photoCard = `
            <div class="my-card" data-category="${photo.category}" id="photo-${photo.id}">
                <a href="/apiPhotos/show?id=${photo.id}">
                    <img src="${photo.url}" class="card-img-top" alt="${photo.description}">
                    <div class="card-body">
                        <h5 class="card-title">${photo.title}</h5>
                        <p class="card-text">${photo.description}</p>
                        <p>Tags:<span>${photo.tag}</span></p>
                    </div>
                </a>
            </div>
        `;
		photoListContainer.insertAdjacentHTML('beforeend', photoCard);
	});
}
