var main = {
    init: function(){
        var _this = this;
        $('#btn_save').on('click', function(){
            _this.save();
        });
        $('#btn_test').on('click',function(){
            alert("버튼 눌림!")
        })
    },
    save: function(){
        var data = {
            title : $('#title').val(),
            author : $('#author').val(),
            content : CKEDITOR.instances['content'].getData()
        };
        console.log(data)
        $.ajax({
            type : 'POST',
            url : '/api/v1/admin/posts/',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.')
            window.location.href="/";
        }).fail(function(err){
            alert(JSON.stringify(err));
        });
    }
};

main.init();