function del(id){

    let password = prompt("비밀번호를 입력해주세요.");
    let url = '/post/'+id+"/del";



    $.ajax({
        type: 'post',
        url: url,
        data: "password="+password,
        success : function(result){
            alert("삭제되었습니다.");
            window.location.replace("/");
        },
        error : function(error){
            alert("비밀번호가 다릅니다.");
        }
    })


}