$(document).ready(function () {

    // $(".blogIndex").click(function () {
    //     let id = $(this).attr("id");
    //     $.ajax({
    //         url: "/yuanzuoBlog/getBlog",
    //         type: "get",
    //             data: {
    //                 id: id
    //             },
    //             success: function (data) {
    //                 if (data.code === 0) {
    //                     $(".page1").html(data.data);
    //                 }
    //             },
    //             error: function () {
    //                 alert("获取失败");
    //             }
    //         })
    //
    // });

    $(".blogIndex").click(function () {
        let id = $(this).attr("id");
        if(id == 1){
            window.open("/yuanzuoBlog/JUC");
        }else if(id == 2){
            window.open("/yuanzuoBlog/JUCQA");
        }else if(id == 3){
            window.open("/yuanzuoBlog/JVM");
        }else if(id == 4){
            window.open("/yuanzuoBlog/JavaDataStructure");
        }else if(id == 5){
            window.open("/yuanzuoBlog/InnoDB");
        }else if(id == 6){
            window.open("/yuanzuoBlog/InnoDBQA");
        }else if(id == 7){
            window.open("/yuanzuoBlog/HighPerformanceMysql");
        }else if(id == 8){
            window.open("/yuanzuoBlog/DBCollection");
        }else if(id == 9){
            //window.open("/yuanzuoBlog/HighPerformanceMysql");
        }else if(id == 10){
            window.open("/yuanzuoBlog/OS");
        }else if(id == 11){
            window.open("/yuanzuoBlog/Puzzle");
        }else if(id == 12){
            window.open("/yuanzuoBlog/Linux");
        }else if(id == 13){
            window.open("/yuanzuoBlog/LinuxQA");
        }else if(id == 14){
            window.open("/yuanzuoBlog/InterfaceDesign");
        }
    });
    $(".sidebar-group").click(function () {
        // if ($(this).find("ul").css("display") === "none") {
        //     $(this).find("ul").css("display", "block");
        // } else {
        //     $(this).find("ul").css("display", "none");
        // }
        $(this).next(".sidebar-heading-next").slideToggle();
    });

});





