var changeBpouton=0; // variable globale servant à afficher ou non le formulaire d'ajout
function Sucess() {
  if (XHR.readyState==4 && XHR.status==200) { 
      console.log("success");
  }
}
/* fonction delete servant à détruire une valeur d'imc (et les informations associées) en cas d'appui du bouton delete */
function Detele( size, weight,imc,date){ 
  var xhr=new XMLHttpRequest(); //XMLHttpRequest permet de communiquer avec le serveur
  xhr.onreadystatechange =  () => { // l'évènement est déclanché à chaque readyState de xhr change.
   if (xhr.readyState === XMLHttpRequest.DONE) {  // si la réquête est finie
     const status = xhr.status;
     if (status === 0 || (status >= 200 && status < 400)) { // status de succès
      const elementDelete = document.getElementById( "row:"+size+":"+weight+":"+imc+":"+date); // retrouve l'élement du tableau de home.js dont les informations ont été supprimees de la BDD 
      elementDelete.remove();// suppression dans le tableau de home.html de la ligne correspondant aux valeurs supprimées dans la BDD
    };
    } else {
      console.log("error 403");
    }
   }
  let name=document.getElementById("usernameSession").innerHTML; // retrouve le nom de l'utilisate
  data=""+name+":"+size+":"+weight+":"+imc+":"+date;// la chaine de caractère envoyé au serveur et traitée dans IndexController
  xhr.open('POST', 'http://localhost:8080/delete'); // crée ou rinitialise une connection avec le serveur avec la méthode et l'URL, la requête est asynchrone
  xhr.setRequestHeader('Access-Control-Allow-Origin', '*');// éviter le problème de CORS
  xhr.send(data); // informations envoyées au serveur 
}

function obtainRecord(){
  var xhr=new XMLHttpRequest();
  xhr.onreadystatechange =  () => {
  if (xhr.readyState === XMLHttpRequest.DONE) {
    const status = xhr.status;
    if (status === 0 || (status >= 200 && status < 400)) {
      let text = xhr.responseText; // recupere les informations envoyées par le serveur
      /*  variable text de la forme [{size=sizeValue, weight=weightValue, imc=imcValue, date=dateValue}, {size=sizeValue, weight=weightValue, imc=imcValue, date=dateValue}, ...] */ 
      text=text.replace("]",""); 
      text=text.replace("[","");
      let sizeLenght="size=";
      let weightLenght="weight=";
      let imclenght="imc=";
      let dateLenght="date=";
      var i=0;
      let size;
      let weight;
      let imc;
      let date;
      const textSplit = text.split(","); // séparation de toutes les valeurs en utilisant le caractère "," 
      textSplit.forEach(element =>  { 
      if(element.match("size")){ // si l'élement contient size (size=sizeValue)
        i=0; // on utilise la variable i afin de savoir si les données constituant un enregistrement ont bien été rassemblée (il y en a 4 : size, weight, imc et date)
        size=element.substr(element.indexOf("size=")+sizeLenght.length); // on recupere la chaine à partir de la position coresspondant à la fin de size=
        i++;
      }
      if(element.match("weight")){
         weight=element.substr(element.indexOf("weight=")+weightLenght.length); // on recupere la chaine à partir de la position coresspondant à la fin de weight=
        i++;
      }
      if(element.match("imc")){
         imc=element.substr(element.indexOf("imc=")+imclenght.length);// on recupere la chaine à partir de la position coresspondant à la fin de imc=
        i++;
      }
      if(element.match("date")){
        date=element.substr(element.indexOf("date=")+dateLenght.length);// on recupere la chaine à partir de la position coresspondant à la fin de date=
        date=date.replace("}","");
       i++;
     }
     // une fois les informations rassemblées, une ligne est ajoutée dans le tableau de la page home.html
      if(i==4){
        const row = document.createElement("tr"); // création de l'élement ligne
        row.id="row:"+size+":"+weight+":"+imc+":"+date; // ajoute d'un id 
        const cellSize = document.createElement("td");// création de la cellule Size
        const cellSizeText = document.createTextNode(""+ size); // ajoute du texte à l'intérieur, on fait de même pour les cellules weight, imc et date
        const cellWeight= document.createElement("td");
        const cellWeightText = document.createTextNode(""+weight);
        const cellImc= document.createElement("td");
        const cellImcText = document.createTextNode(""+imc);
        const cellDate= document.createElement("td");
        const cellDateText = document.createTextNode(""+date);
        const cellDelete=document.createElement("td");
        const deleteButton=document.createElement("button"); // ajoute d'une cellule pour pouvoir supprimer la valeur
        deleteButton.type="click"; // ajout d'un type
        deleteButton.className="btn btn-danger";
        deleteButton.id=""+size+":"+weight+":"+imc+":"+date; // ajoute d'un id pour le bouton delete
        deleteButton.value="delete";
        deleteButton.innerHTML="delete";
        cellDelete.appendChild(deleteButton); // ajoute du bouton delete dans la cellule correspondant au delete
        let table=document.getElementById("recordImcSession"); // recuperation du tableau de la page home.thml
        cellSize.appendChild(cellSizeText); // ajoute du text dans la cellule Size
        row.appendChild(cellSize);// ajout à l'élement colonne de ma cellule size, on fait de même pour weight, imc, et data
        cellWeight.appendChild(cellWeightText);
        row.appendChild(cellWeight);
        cellImc.appendChild(cellImcText);
        row.appendChild(cellImc);
        cellDate.appendChild(cellDateText);
        row.appendChild(cellDate);
        row.appendChild(cellDelete)
        table.appendChild(row); // ajout de ligne au tableau
      } 
    });
    } else {
      console.log("error 403");
    }
  }
  };
  xhr.open('POST', 'http://localhost:8080/obtainperson'); 
  xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
  data=""+document.getElementById("usernameSession").innerHTML; // ajoute du username à la reqête
  xhr.send(data);
}

obtainRecord(); // appel de la fonction obtainRecord

/* fonction envoyant les données */
function sendRecord(){
 var form=document.getElementById('userrecord');
  var XHR=new XMLHttpRequest();
  XHR.onreadystatechange = () => {
    if ( XHR.readyState === XMLHttpRequest.DONE) {
      const status =  XHR.status;
      if (status === 0 || (status >= 200 && status < 400)) {
       // console.log("success add")
          let text = XHR.responseText;
          /* reponse du serveur de la forme [{size="sizeValue",weight="weightValue",imc="imcValue",date="YYYY-MM-DD"}) */
          text=text.replace("]","");
          text=text.replace("[","");
          let sizeLenght="size=";
          let weightLenght="weight=";
          let imclenght="imc=";
          let dateLenght="date=";
          var i=0;
          let size;
          let weight;
          let imc;
          let date;
          const textSplit = text.split(",");
          textSplit.forEach(element =>  {
          //  même principe que dans la fonction  obtainRecord()
          if(element.match("size")){
            i=0;
             size=element.substr(element.indexOf("size=")+sizeLenght.length);
            i++;
          }
          if(element.match("weight")){
             weight=element.substr(element.indexOf("weight=")+weightLenght.length);
            i++;
          }
          if(element.match("imc")){
             imc=element.substr(element.indexOf("imc=")+imclenght.length);
            i++;
          }
          if(element.match("date")){
            date=element.substr(element.indexOf("date=")+dateLenght.length);
            date=date.replace("}","");
           i++;
         }
          if(i==4){
            const row = document.createElement("tr");
            row.id="row:"+size+":"+weight+":"+imc+":"+date;
            const cellSize = document.createElement("td");
            const cellSizeText = document.createTextNode(""+ size);
            const cellWeight= document.createElement("td");
            const cellWeightText = document.createTextNode(""+weight);
            const cellImc= document.createElement("td");
            const cellImcText = document.createTextNode(""+imc);
            const cellDate= document.createElement("td");
            const cellDateText = document.createTextNode(""+date);
            const cellDelete=document.createElement("td");
            const deleteButton=document.createElement("button");
            deleteButton.type="click";
            deleteButton.id=""+size+":"+weight+":"+imc+":"+date;
            deleteButton.value="delete";
            deleteButton.innerHTML="delete";
            deleteButton.className="btn btn-danger";
            deleteButton.value="delete";
            cellDelete.appendChild(deleteButton);
            let table=document.getElementById("recordImcSession");
            cellSize.appendChild(cellSizeText);
            row.appendChild(cellSize);
            cellWeight.appendChild(cellWeightText);
            row.appendChild(cellWeight);
            cellImc.appendChild(cellImcText);
            row.appendChild(cellImc);
            cellDate.appendChild(cellDateText);
            row.appendChild(cellDate);
            row.appendChild(cellDelete)
            table.appendChild(row);
          }
          });
        }
      } else {
        console.log("error 403");
      }
    }
  var data=new FormData(form);// FormData permet de construire un tableau de clef-valeur représentant lec champs du formualaire
  var json = JSON.stringify(Object.fromEntries(data)); // convertit le tableau en chaîne de caractère  
  //json est de la forme suivante {"name":"valeurName", "size":"valeurSize", "weight":"valeurWeight"}
  XHR.open('POST', 'http://localhost:8080/recordperson');
  XHR.setRequestHeader('Access-Control-Allow-Origin', '*');
  XHR.send(json);

}

/*ajoute un listener au bouton ajouter */
document.getElementById('ajouter').addEventListener("click", function() {

   if(changeBpouton==0){ // si la variable gloable est à 0, on affiche le formulaire
    document.getElementById("demo").innerHTML = "<form   id='userrecord' th:object='${person}'  ><div class='mb-3 row'><label class='col-sm-2 col-form-label' style='display:none;' >Name</label><div class='col-sm-10'><input style='display:none;' type='text'   class='form-control' id='name' name='name' th:field=' *{name}' value='"+document.getElementById("usernameSession").innerHTML+"' th:text='"+ document.getElementById("usernameSession").innerHTML + "' th:value='"+ document.getElementById("usernameSession").innerHTML+"' ></input></div></div> <div class='mb-3 row'><label  class='col-sm-2 col-form-label'  >taille  (en m)</label> <div class='col-sm-10'><input type='text' class='form-control'  th:field='*{size}'  id='size' name='size' placeholder='1.6'></input></div></div><div class='mb-3 row'><label  class='col-sm-2 col-form-label' >Poids (Kg)</label><div class='col-sm-10'><input type='text' class='form-control'  name='weight'  th:field='*{weight}' id='weight' placeholder='80'></input> </div></div><div class='btn'> <button type='button' value='formIMC' id='addRecord' class='btn btn-primary mb-3' >Submit</button></div> </form>";
    changeBpouton=1;
   }else{ //le formulaire est caché
    document.getElementById("demo").innerHTML = "";
    changeBpouton=0;
   }
  }); 


document.body.addEventListener( 'click', function ( event ) {
  // si le bouton cliqué correspond à addRecord 
    if( event.target.id == 'addRecord' ) { 
      sendRecord(); // appel de la fonction sendRecord
      document.getElementById("userrecord").reset(); // les valeur renseignées dans le formulaire d'ajout de la page html sont supprimées
    };
     // si le bouton cliqué correspond à un bouton delete
    if(event.target.value=="delete"){      
            let text=event.target.id; // recupération de l'id du bouton
            //l'id est de la forme suivante "username:valeurSize:valeurWeight:valeurImc:valeurDate"
            let sizeButton;
            let weightButton;
            let dateButton;
            let imcButton;
            var i=0;
            const textButtonSplit = text.split(":");
            // Tous les bouton delete de la page ont un id de cette forme, la récupération se fait simplement 
            textButtonSplit.forEach(element =>  {
                if(i ==0){ // premier element correspond à la taille
                  sizeButton=element;
                }
                if(i ==1){ // deuxième element correspond au poids
                  weightButton=element;
                }
                if(i ==2){// troisième element correspond à l'imc
                  imcButton=element;
                }
                if(i ==3){// troisième element correspond à la date
                  dateButton=element;
                }
                i++;
              
            });
            Detele(sizeButton, weightButton,imcButton,dateButton); // appel de la fonction Delete
    };
   
  } );

