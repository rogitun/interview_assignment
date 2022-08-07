function addComment(pId){

    let writer = $('#writer').val();
    let password = $('#password').val();
    let content = $('#content').val();

    if(!writer || !password || !content){
        alert("모든 항목을 기입해주세요.");
        return;
    }

    let comment = new Object();
    comment.writer = writer;
    comment.password = password;
    comment.content = content;

    let nextUrl = "/post/"+pId+"/addComment";
    $.ajax({
        type: 'post',
        url : nextUrl,
        headers: {"Content-Type": "application/json"},
        data: JSON.stringify(comment),
        success: function(result){
            alert("등록되었습니다.");
            window.location.reload();
        },
        error: function(error){
            console.log(error);
            alert("잘못된 접근입니다.");
        }
    })
}

function delComment(cId){
    let inputPwd = prompt("비밀번호를 입력해주세요.");
    let nextUrl = "/comment/"+cId+"/del";
    $.ajax({
        type: 'post',
        url: nextUrl,
        data: "password="+inputPwd,
        success: function(result){
            console.log(result);
            alert(result);
        },
        error: function(error){
            console.log(error);
            alert(error);
        }
    })
}