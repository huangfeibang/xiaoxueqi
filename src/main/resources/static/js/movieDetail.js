$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;
    var data;

    $('#more').href = '/user/movie/more?id='+movieId;
    console.log($('#more').href);
    getMovie();
    if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();



    function getMovieLikeChart() {
       getRequest(
           '/movie/' + movieId + '/like/date',
           function(res){
                    var likedata = res.content,
                    dateArray = [],
                    numberArray = [];
                    likedata.forEach(function (item) {
                    dateArray.push(item.likeTime);
                    numberArray.push(item.likeNum);
               });

               var myChart = echarts.init($("#like-date-chart")[0]);

               // 指定图表的配置项和数据
               var option = {
                   title: {
                       text: '想看人数变化表'
                   },
                   xAxis: {
                       type: 'category',
                       likedata: dateArray
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       likedata: numberArray,
                       type: 'line'
                   }]
               };

               // 使用刚指定的配置项和数据显示图表。
               myChart.setOption(option);
           },
           function (error) {
               alert(error);
           }
       );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                data = res.content;
                isLike = data.islike;
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }


    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
    }

    // user界面才有


    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
             url,
            null,
            function (res) {
                 isLike = !isLike;
                 getMovie();
            },
            function (error) {
                alert(error);
            });
    });


    $("#edit-btn").click(function(){
        //alert(data.posterUrl);
        $('#movie-edit-name-input').attr('value',data.name);
        $('#movie-edit-startDate-input').val(data.startDate.slice(0,16));
        $('#movie-edit-img-input').attr('value',data.posterUrl);
        $('#movie-edit-story-input').text(data.description);
        $('#movie-edit-type-input').attr('value',data.type);
        $('#movie-edit-length-input').attr('value',data.length);
        $('#movie-edit-star-input').attr('value',data.starring);
        $('#movie-edit-director-input').attr('value',data.director);
        $('#movie-edit-country-input').attr('value',data.country);
        $('#movie-edit-language-input').attr('value',data.language);
        $('#movie-edit-writer-input').attr('value',data.screenWriter);
    });


    // admin界面才有
    $("#modify-btn").click(function () {
       var form=getMovieEditForm();
       if(validateMovieForm(form)) {

           postRequest(
               '/movie/update',
               form,
               function (res) {
                   if (res.success) {
                       getMovie();
                       $("#movieEditModal").modal('hide');
                   } else {
                       alert(res.message);
                   }
               },
               function (error) {
                   alert(JSON.stringify(error));
               }
           );
       }
    });
    $("#delete-btn").click(function () {
        var r=confirm("确认要下架该电影吗")
                if (r) {
                    postRequest(
                        '/movie/off/batch',
                        {movieIdList:[data.id]},
                        function (res) {
                            if(res.success){
                                //getMovie();
                                //$('.movie-on-list').delete(res.content);
                                // $("#movieOffBatchModal").modal('hide');
                                alert("下架成功");
                            } else{
                                alert(res.message);
                            }
                        },
                        function (error) {
                            alert(JSON.stringify(error));
                        }
                    );
                }
    });
    $("#deleteMovie-btn").click(function () {
        var r=confirm("确认要删除该电影吗")
        if (r) {
            postRequest(
                '/movie/delete',
                {movieIdList:[data.id]},
                function (res) {
                    if(res.success){
                        //getMovie();
                        //$('.movie-on-list').delete(res.content);
                        // $("#movieOffBatchModal").modal('hide');
                        alert("删除成功");
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });

    function getMovieEditForm() {
        return {
            id: data.id,
            name: $('#movie-edit-name-input').val(),
            startDate: $('#movie-edit-date-input').val(),
            posterUrl: $('#movie-edit-img-input').val(),
            description: $('#movie-edit-story-input').val(),
            type: $('#movie-edit-type-input').val(),
            length: $('#movie-edit-length-input').val(),
            country: $('#movie-edit-country-input').val(),
            starring: $('#movie-edit-star-input').val(),
            director: $('#movie-edit-director-input').val(),
            screenWriter: $('#movie-edit-writer-input').val(),
            language: $('#movie-edit-language-input').val(),
            states:data.status
        };
    }
    function validateMovieForm(data) {
            var isValidate = true;
            if(!data.name) {
                isValidate = false;
                $('#movie-edit-name-input').parent('.form-group').addClass('has-error');
            }
            if(!data.posterUrl) {
                isValidate = false;
                $('#movie-edit-img-input').parent('.form-group').addClass('has-error');
            }
            if(!data.startDate) {
                isValidate = false;
                $('#movie-edit-date-input').parent('.form-group').addClass('has-error');
            }
            return isValidate;
        }
});