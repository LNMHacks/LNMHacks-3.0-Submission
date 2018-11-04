$(document).ready(function() {
$(".choose div label").on("click", function() {
    $(this).parent().toggleClass("active").siblings().removeClass("active")
 
});
  
  
  $(".choose div").on("click", function(){
    $(this).toggleClass("active").siblings().removeClass("active");
    
  });



  });



