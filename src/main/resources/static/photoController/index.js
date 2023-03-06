console.log("Index: JS OK!");

photoList();

function photoList() {
	const photoListContainer = document.querySelector('#photo-list');
	const categorySelect = document.querySelector('#category-select');

	axios.get('http://localhost:8080/api/photos')
		.then((res) => {
			const photosData = res.data.photos;
			const categories = res.data.categories;
			categories.forEach(category => {
				const option = `<option value="${category.name}">${category.name}</option>`;
				categorySelect.insertAdjacentHTML('beforeend', option);
			});

			categorySelect.addEventListener('change', () => {
				const selectedCategory = categorySelect.value;
				photosData.forEach(photo => {
					if (selectedCategory === '' || photo.categories.some(category => category.name === selectedCategory)) {
						photoListContainer.querySelector(`#photo-${photo.id}`).classList.remove('d-none');
					} else {
						photoListContainer.querySelector(`#photo-${photo.id}`).classList.add('d-none');
					}
				});
			});

			photosData.forEach(photo => {
				const photoCard = `
			        <div class="my-card" data-category="${photo.category}" id="photo-${photo.id}">
			        	<a href="/apiPhotos/show?id=${photo.id}">
			        		<img src="${photo.url}" class="card-img-top" alt="${photo.description}">
			        		<div class="card-body">
			        		<h5 class="card-title">${photo.title}</h5>
			        		<p class="card-text">${photo.description}</p>
			        		<p>Tags:<span>${photo.tag}</span></p>   		
			        	</a>
			        </div>
			      `;
				photoListContainer.insertAdjacentHTML('beforeend', photoCard);
			});
		})
		.catch((err) => {
			console.error('Errore nella richiesta', err);
			alert('Errore durante la richiesta!');
		});
}


