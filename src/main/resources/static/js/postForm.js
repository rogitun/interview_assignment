function addPost(){

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

    $.ajax({
        type: 'post',
        url : '/add-post',
        headers: {"Content-Type": "application/json"},
        data : JSON.stringify(obj),
        success : function(result){
            alert("등록되었습니다.");
            window.location.replace("/");
        },
        error : function(error){
            alert("잘못된 접근입니다.");
        }
    })
}