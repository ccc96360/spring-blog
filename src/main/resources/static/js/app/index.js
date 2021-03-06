var main = {
    init: function(){
        var _this = this;
        $('#btn_save').on('click', function(){
            _this.save();
        });
        $('#btn_test').on('click',function(){
            var tags = $("select").val();
            alert("버튼 눌림!");
            console.log(tags)
        });
        $('#btn_update').on('click', function(){
            console.log("Update 버튼 눌림");
            _this.update();
        });
        $('#btn_delete').on('click', function(){
            console.log("Delete 버튼 눌림");
            _this.delete();
        });
        $('#btn_search').on('click', function(){
            _this.search();
        });
    },
    save: function(){
        var data = {
            title : $('#title').val(),
            author : $('#author').val(),
            content : CKEDITOR.instances['content'].getData()
        };
        data.tags = $("select").val();
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
    },
    update: function(){
        var data = {
            title : $('#title').val(),
            content : CKEDITOR.instances['content'].getData()
        }
        var a = $("select").val();
        data.tags = Array.from(new Set(a.values()));
        var id = $('#id').val();
        console.log(JSON.stringify(data))

        $.ajax({
            type: 'PUT',
            url: "/api/v1/admin/posts/"+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 수정되었습니다.');
            window.location.href='/post/'+id;
        }).fail(function(err){
            alert(JSON.stringify(err));
        })  
    },
    delete: function(){
        var id = $('#id').val();
        $.ajax({
            type: 'DELETE',
            url: "/api/v1/admin/posts/" + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제 되었습니다.');
            window.location.href = '/';
        }).fail(function(err) {
            alert(JSON.stringify(err))
        });
    },
    search: function(){
        var keyword = $('#keyword').val();
        window.location.href='/search/'+ keyword;
    }


};

main.init();