function del(id){

    let password = prompt("비밀번호를 입력해주세요.");
    let url = '/post/'+id+"/del";

    $.ajax({
        type: 'post',
        url: url,
        data: "password="+password,
        success : function(result){
            alert(result);
            window.location.replace("/");
        },
        error : function(error){
            alert(error.responseText);
        }
    })
}

//function passwordCheck(id){
//
//    let password = prompt("비밀번호를 입력해주세요.");
//    let url = '/post/'+id+"/tryEdit";
//
//    $.ajax({
//        type: 'post',
//        url: url,
//        data: "password="+password,
//        success : function(result){
//            edit(id);
//        },
//        error : function(error){
//            alert(error.responseText);
//        }
//    })
//}

//function edit(id){
//
//    let password = prompt("비밀번호를 입력해주세요.");
//    let url = '/post/'+id+"/edit";
//
//    $.ajax({
//        type: 'post',
//        url: url,
//        data: "password="+password,
//        success : function(result){
//            window.location.replace("/");
//        },
//        error : function(error){
//            alert(error.responseText);
//        }
//    })
//}


function likePost(pId){
    let nextUrl = "/post/"+pId + "/like";

    $.ajax({
        type: 'post',
        url : nextUrl,
        data: "operation=add",
        success: function(result){
            alert(result);
        },
        error: function(error){
            alert(error.responseText);
        }
    })
}

function disLikePost(pId){
    let nextUrl = "/post/"+pId + "/like";

    $.ajax({
        type: 'post',
        url : nextUrl,
        data: "operation=sub",
        success: function(result){
            alert(result);
        },
        error: function(error){
            alert(error.responseText);
        }
    })
}
