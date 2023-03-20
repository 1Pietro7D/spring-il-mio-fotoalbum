console.log("Show: JS OK!");

const urlParams = new URLSearchParams(window.location.search);
const photoId = parseInt(urlParams.get('id'));


const valueToken = document.getElementById("x").value;
const nameToken = document.getElementById("xh").value;

// Aggiungi il token a ogni richiesta AJAX
axios.interceptors.request.use(function (config) {
  if (config.method === 'get') {
    return config; // restituisce la configurazione senza modifiche
  }
  config.headers[nameToken] = valueToken;
  return config;
});

showPhoto(photoId);
showComments(photoId);

function showPhoto(photoId) {
	axios.get(`http://localhost:8080/api/photos/${photoId}`)
		.then((res) => {
			const photo = res.data;
			const photoContainer = document.querySelector('#photo-details');

			const photoCard = `
      <div class="card">
        <img src="${photo.url}" alt="${photo.title}">
        <div>
          <h5>Title: ${photo.title}</h5>
          <p>${photo.description}</p>
          <p>Tags: ${photo.tag}</p>
          <div>
            <a href="/apiPhotos" class="btn btn-primary">Back to index</a>
          </div>
        </div>
      </div>
    `;
			photoContainer.insertAdjacentHTML('beforeend', photoCard);
		})
		.catch((err) => {
			console.error('Errore nella richiesta', err);
			alert('Errore durante la richiesta!');
		});
}

// RECUPERO COMMENTI
function showComments(photoId) {
	axios.get(`http://localhost:8080/api/photos/${photoId}`)
		.then((res) => {
			const comments = res.data.comments;
			const commentContainer = document.querySelector('#comments');

			if (comments.length > 0) {
				commentContainer.innerHTML = '<h3>Comments:</h3>';

				comments.forEach((comment) => {
					const commentDiv = document.createElement('div');
					commentDiv.classList.add('comment');
					commentContainer.appendChild(commentDiv);

					const username = document.createElement('span');
					username.classList.add('username');
					username.innerHTML = `Username: ${comment.username}`;
					commentDiv.appendChild(username);

					const content = document.createElement('div');
					content.classList.add('content');
					content.innerHTML = `Comment: ${comment.content}`;
					commentDiv.appendChild(content);

					const hr = document.createElement('hr');
					commentDiv.appendChild(hr);
				});
			} else {
				commentContainer.innerHTML = '<p>No comments yet.</p>';
			}
		})
		.catch((err) => {
			console.error('Errore nella richiesta', err);
			alert('Errore durante la richiesta!');
		});
}



// AGGIUNTA COMMENTO
function addComment(photoId) {
	const username = document.querySelector('#username').value;
	const content = document.querySelector('#content').value;

	axios.post(`http://localhost:8080/api/photos/${photoId}/comments`, {
		username: username,
		content: content
	})
		.then((res) => {
			console.log(res.data);
			document.querySelector('#username').value = '';
			document.querySelector('#content').value = '';
			showComments(photoId);
		})
		.catch((err) => {
			console.error('Errore nella richiesta', err);
			alert('Errore durante la richiesta!');
		});
}

