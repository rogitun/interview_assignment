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
    });
};

function delComment(cId){
    let inputPwd = prompt("비밀번호를 입력해주세요.");
    let nextUrl = "/comment/"+cId+"/del";



    $.ajax({
        type: 'post',
        url: nextUrl,
        data: "password="+inputPwd,
        success: function(result){
            alert(result);
            $('#content_'+cId).text("삭제된 댓글입니다.");
            //window.location.reload();
        },
        error: function(error){
            console.log(error);
            alert(error.responseText);
        }
    })
}


function addMoreCmt(comments){
    //id="commentList 하위에 삭제
    const div = document.getElementById('commentList');

    for(let i=0;i<comments.length;i++){
    const outerDiv = document.createElement("div");
    outerDiv.setAttribute("class","comment");

    const innerDiv = document.createElement("div");
    innerDiv.setAttribute("class","comment__details");

    const outerA = document.createElement("a");
    outerA.setAttribute("class","comment__author");
    //-> text에 작성자

    const innerSpan = document.createElement("span");
    //-> text에 생성일

    const innerA = document.createElement("a");
    innerA.setAttribute("class","tag tag--pill tag--main settings__btn")
    //-> onclick 삭제함수

    const contentP = document.createElement('p');
    contentP.setAttribute("class","comment__info");
        contentP.setAttribute("id","content_"+comments[i].id);
    //-> text에 댓글 내용


     outerA.innerText += comments[i].writer;
     innerSpan.innerText += (" on (" +  comments[i].created + ") ");
     innerA.innerText += "삭제";
     innerA.setAttribute("onClick","delComment(" +comments[i].id +")");
     contentP.innerText += comments[i].content;

        innerDiv.appendChild(outerA);
        innerDiv.appendChild(innerSpan);
        innerDiv.appendChild(innerA);
        innerDiv.appendChild(contentP);
        outerDiv.appendChild(innerDiv);
        div.appendChild(outerDiv);

        //innerA.addEventListener("click",delComment(comments[i].id));
   }
}


function showMoreCmt(pId){
    let nextPage = parseInt($('#nextPage').val());
    let nextUrl = "/post/"+pId + "/moreComment?page=" + nextPage;

    $.ajax({
        type: 'get',
        url : nextUrl,
        async: false,
        success: function(result){
            console.log(result);
            $('#nextPage').val(nextPage+1);
            if(result){
                addMoreCmt(result.comments);
            }
            else alert("불러올 댓글이 없습니다.");
        },
        error:function(error){
            console.log(error);
        }
    })
}

//<div class="comment" th:each="c : ${comments}">
//      <div class="comment__details">
//         <a class="comment__author" th:text="${c.writer}">댓글 작성자</a><span
//               th:text="| on (${c.created})|"></span>
//                   <a class="tag tag--pill tag--main settings__btn"
//                        th:onclick="delComment([[${c.id}]])">삭제
//                    </a>
//        <p class="comment__info" th:text="${c.content}"> 댓글 본문 영역</p>
//</div>

