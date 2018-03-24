var myFile = "";
class XHR{
    //dat va a ser el json proveniente del form y url va a ser el servlet al cual se quiere enviar
 login(url,data){
     var x= new XMLHttpRequest();
     x.onreadystatechange=function(){
    if((this.readyState==4)&&(this.status==200)){
    	var r=JSON.parse(x.response);
        console.log(x.responseText);
        console.log(toString(r.status));
        alert("el usuario "+r.username+" ha iniciado sesion con exito");
        if(r.url!=null){
        window.top.location.href=r.url;
        }
    }else{
        console.log(x.statusText);
    }
}
    x.open("post",url,true);
    x.setRequestHeader('Content-Type','application/Json');
    x.send(JSON.stringify(data));
}
 get(url){
    var x= new XMLHttpRequest();
     x.onreadystatechange=function(){
	    if((this.readyState==4)&&(this.status==200)){
	        var r=JSON.parse(x.response);
	        window.top.location.href=r.url;
	     }else{
	         console.log(x.statusText);
	     }
 }
     x.open("get",url,true);
     x.send();
 }
 up(){
		var x= new XMLHttpRequest();
		var formData = new FormData();
		formData.append("file", $("file").files[0]);
		formData.append("titulovid",$("titulovid").value.trim());
		formData.append("deArchivo",$("deArchivo").value.trim());
		//myFile = $("file").files[0].name;
		x.onreadystatechange = function () {
			if (x.status === 200 && x.readyState === 4) {
				$("uploadStatus").textContent = x.responseText + "\nFile uploaded";
				$("loading").style="visibility:hidden";
			}
		}

		x.open("post", "./SubirArchivo", true);
		x.send(formData);

 }
 like(videoname){
	 var x= new XMLHttpRequest();
	 x.onreadystatechange=function(){
		    if((this.readyState==4)&&(this.status==200)){

		     }else{
		         console.log(x.statusText);
		     }
	 }

x.open("get","/Entrega1/LikeServlet?videoname="+videoname ,true);
x.send();
}

dislike(videoname){
	 var x= new XMLHttpRequest();
	 x.onreadystatechange=function(){
		    if((this.readyState==4)&&(this.status==200)){

		     }else{
		         console.log(x.statusText);
		     }
	 }

x.open("get","/Entrega1/DislikeServlet?videoname="+videoname ,true);
x.send();
 }

 comment(videoname,comment){
	 var x= new XMLHttpRequest();
	 var formData= new FormData();
	 formData.append("videoname",$("nombreVid").value.trim());
	 formData.append("comment",comment);
	 x.onreadystatechange=function(){
		    if((this.readyState==4)&&(this.status==200)){

		     }else{
		        	 console.log(x.responseText+"error");
		     }
	 }
	 x.open("post","/Entrega1/CommentsServlet?videoname="+videoname,true);
	 x.send(formData);
 }
}
