ci hanno commissionato un lavoro : un fotografo vuole mostrare agli utenti le foto più belle che ha scattato e ci chiede di realizzare una webapp che permetta questo.

Ha bisogno di un’area di amministrazione per gestire le foto, quindi
- vedere tutte quelle inserite (filtrabili)
- vedere i dettagli di una singola foto
- aggiungerne di nuove (con validazione)
- modificarle (con validazione)
- cancellarle

Ovviamente queste operazioni può svolgerle solo lui, quindi l’accesso alle pagine deve essere protetto da autenticazione.
Una foto contiene almeno le seguenti informazioni :
- titolo
- descrizione
- url
- tag
- visibile
- categorie

Una foto può essere collegata a più categorie, e una categoria può essere collegata a più foto.
Prevedere quindi anche una semplice pagina di creazione, modifica, cancellazione categorie.
Deve essere presente anche una homepage pubblica, nella quale le foto (visibili) sono mostrate agli utenti.
Devono essere filtrabili per titolo e tag.
Ogni foto può essere commentata dagli utenti, quindi prevedere nella pagina pubblica di dettaglio un’area per aggiungere un nuovo commento e una per mostrare l’elenco di quelli già inviati.
L’area di amministrazione va realizzata sfruttando Thymeleaf + controller MVC.
Tutte le pagine pubbliche e l’invio dei commenti devono essere gestite tramite javascript + webapi. 