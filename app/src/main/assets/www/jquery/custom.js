
$('document').ready(function(){
	
	$('#send').click(function(event){
		var suggestion = $('#textField').val();
		var message = "Please enter your Suggestion.";
	
		if(suggestion!=""){
			message="Upload Successful";
			Android.saveSuggestion(suggestion,message);	
		}else{
			$('#textField').css("border-bottom-color","#FF4081");
			$('#textField').css("border-left-color","#FF4081");
			$('#textField').css("border-size","3px");
			Android.alert(message);
		}
	});
});
