function nextPage(num){
    let search = $('#search').val();
    let category = $('#select').val();
    let nextUrl = "?page=" + num;
    console.log(category + " " + search);
    if(!!category && !!search) nextUrl += ("&category=" + category + "&search=" + search);

    document.getElementById("page"+num).className='btn btn--sub';
    location.href = nextUrl;
}

$(window).on('load',function(){
//alert("ㅗㅑㅗㅑ");
    let curPage = parseInt($('#curPage').val());
    let curPageId = "page"+(curPage+1);
    //alert(curPageId);
    document.getElementById(curPageId).className='btn btn--sub';
  //  console.log(search + " " + category);

})