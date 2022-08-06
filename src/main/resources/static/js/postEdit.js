$(document).ready(function(){

 while(true){
    let pwd = $('#pwd').val();
    let tryPwd = prompt("비밀번호를 입력하세요.");
    let pid = $('#pid').val();
    if(tryPwd==null){
        let url = "/post/"+pid;
        window.location.replace(url);
        break;
    }

    let flag;

    $.ajax({
        type: 'post',
        url: "/post/" + pid + "/pwdCheck",
        data: "password="+tryPwd,
        async: false,
        success: function(result){
            flag = true;
        },
        error: function(error){
            flag= false;
        },
    });

    if(flag){
        let doc = document.getElementById("formWrap");
        doc.style.display = 'block';
        break;
    }
    else{
        alert("비밀번호가 일치하지 않습니다.");
    }

//    if(pwd==tryPwd) {
//        let doc = document.getElementById("formWrap");
//        doc.style.display = 'block';
//        break;
//    }
//    else{
//        alert("비밀번호가 일치하지 않습니다.");
//    }
 }
})

function edit(pid,encode){
        let pwd = encode;

        let title = $('#title').val();
        let content = $('#content').val();

        let writer = $('#writer').val();
        let password = $('#password').val();

        let hashtags = $('#hashtag').val();

        if(!title || !content || !writer || !password){
            alert("필수 입력칸을 작성해주세요!");
            return;
        }

        let idx = hashtags.indexOf('#');

        if(idx >= 0){
            let hash = hashtags.substring(idx+1,hashtags.length);
            const hts = hash.split("#");
            if(hts.length>5){
                alert("해쉬태그는 최대 5개까지 등록 가능합니다.");
                return;
            }
            else{
                hashtags = hash;
            }
        }

        let obj = new Object();
        obj.title = title;
        obj.content = content;
        obj.writer = writer;
        obj.password = password;
        obj.hashtag = hashtags;
        obj.pwd = pwd;

        $.ajax({
            type: 'post',
            url : '/post/'+pid+"/edit",
            headers: {"Content-Type": "application/json"},
            data : JSON.stringify(obj),
            success : function(result){
                alert("변경되었습니다.");
               window.location.replace("/post/"+pid);
            },
            error : function(error){
                alert("변경에 실패했습니다");
            }
        })
}
