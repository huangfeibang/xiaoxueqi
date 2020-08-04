var user_name = sessionStorage.getItem('username');

var activities = [];
$(document).ready(function () {
    $("#admin-name").html(user_name);
    getAllMovies();

    getActivities();

    function getActivities() {
        getRequest(
            '/activity/get',
            function (res) {
                activities = res.content;
                renderActivities(activities);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderActivities(activities) {
        $(".content-activity").empty();
        var activitiesDomStr = "";

        activities.forEach(function (activity) {
            var movieDomStr = "";
            if (activity.movieList.length) {
                activity.movieList.forEach(function (movie) {
                    movieDomStr += "<li class='activity-movie primary-text'>" + movie.name + "</li>";
                });
            } else {
                movieDomStr = "<li class='activity-movie primary-text'>所有热映电影</li>";
            }

            activitiesDomStr +=
                "<div class='activity-container'>" +
                "    <div class='activity-card card'>" +
                "       <div class='activity-line'>" +
                "           <span class='title'>" + activity.name + "</span>" +
                "           <span class='gray-text'>" + activity.description + "</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>活动时间：" + formatDate(new Date(activity.startTime)) + "至" + formatDate(new Date(activity.endTime)) + "</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>参与电影：</span>" +
                "               <ul>" + movieDomStr + "</ul>" +
                "       </div>" +
                "    </div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>优惠券：" + activity.coupon.name + "</span>" +
                "        <span class='title'>满" + activity.coupon.targetAmount + "减<span class='error-text title'>" + activity.coupon.discountAmount + "</span></span>" +
                "        <span class='gray-text'>" + activity.coupon.description + "</span>" +
                "        <span class='gray-text'>" + "活动: " + activity.id + "</span>" +
                "    </div>" +
                "</div>";
        });
        $(".content-activity").append(activitiesDomStr);
    }

    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                //$('#activity-movie-input').append("<option value="+ -1 +">所有电影</option>");
                movieList.forEach(function (movie) {
                    $('#activity-movie-input').append("<option value=" + movie.id + ">" + movie.name + "</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    $("#activity-form-btn").click(function () {
        var form = {
            name: $("#activity-name-input").val(),
            description: $("#activity-description-input").val(),
            startTime: $("#activity-start-date-input").val(),
            endTime: $("#activity-end-date-input").val(),
            movieList: [...selectedMovieIds
    ],
        couponForm: {
            description: $("#coupon-name-input").val(),
                name
        :
            $("#coupon-description-input").val(),
                targetAmount
        :
            $("#coupon-target-input").val(),
                discountAmount
        :
            $("#coupon-discount-input").val(),
                startTime
        :
            $("#activity-start-date-input").val(),
                endTime
        :
            $("#activity-end-date-input").val()
        }
    }
        ;

        postRequest(
            '/activity/publish',
            form,
            function (res) {
                if (res.success) {
                    getActivities();
                    $("#activityModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    //ES6新api 不重复集合 Set
    var selectedMovieIds = new Set();
    var selectedMovieNames = new Set();

    $('#activity-movie-input').change(function () {
        var movieId = $('#activity-movie-input').val();
        var movieName = $('#activity-movie-input').children('option:selected').text();
        if (movieId == -1) {
            selectedMovieIds.clear();
            selectedMovieNames.clear();
        } else {
            selectedMovieIds.add(movieId);
            selectedMovieNames.add(movieName);
        }
        renderSelectedMovies();
    });

    //渲染选择的参加活动的电影
    function renderSelectedMovies() {
        $('#selected-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>" + movieName + "</span>";
        });
        $('#selected-movies').append(moviesDomStr);
    }

    $("#activity-drop-btn").click(function () {
        var a = 1;
        $("#activity-drop-id-input").empty();
        for (var i = 0; i < activities.length; i++) {
            $("#activity-drop-id-input").append("<option value=" + activities[i].id + ">" + activities[i].id + "</option>");  //为Select追加一个Option(下拉项)
            a++;
        }

    });

    $("#activity-delete-btn").click(function () {
        var r = confirm("确认要删除该活动吗？");
        if (r) {
            var target = $("#activity-drop-id-input").children('option:selected').val();
            postRequest(
                '/activity/delete?id=' + target,
                null,
                function (res) {
                    alert("删除成功");
                    activities = res.content;
                    renderActivities(activities);
                    $("#deleteActivity").modal('hide');
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            )
        }
    });
});