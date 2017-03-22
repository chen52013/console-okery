/**
 * Created by Viki on 2016/5/19.
 */
var echartsUtil = new Object();

echartsUtil.tips_formatter = function(params) {
    return params[0].name + ' : ' + params[0].value[1];
};


echartsUtil.is_in_arr = function(property, array){
    for(var i in array){
        if(property===array[i]){
            return true;
        }
    }
    return false;
};

echartsUtil.plot_time_series_by_group = function(element_id, title, subtitle, y_axis_max_value, data, data_name_colume, data_value_column, data_group_column, y_axis_names, groups_in_sec_y_axis){
    var myChart = echarts.init(document.getElementById(element_id));
    var new_series_map = {};
    var legend_data = [];
    var new_series_arr = [];

    var y_axis_obj = [];
    if(y_axis_names == null || y_axis_names == '' || typeof(y_axis_names) == 'undefined' || y_axis_names.length == 0){
        var y_axis_d1_max_value = (y_axis_max_value == null || y_axis_max_value == '' || typeof(y_axis_max_value) == 'undefined' || y_axis_max_value.length == 0 || y_axis_max_value[0] == null || y_axis_max_value[0] == '' || typeof(y_axis_max_value[0]) == 'undefined') ? 'auto' : y_axis_max_value[0];
        y_axis_obj.push({
            type:'value',
            max:y_axis_d1_max_value,
            boundaryGap: [0, '100%']
        });
    }else if(y_axis_names.length == 1){
        var y_axis_d1_max_value = (y_axis_max_value == null || y_axis_max_value == '' || typeof(y_axis_max_value) == 'undefined' || y_axis_max_value.length < 1 || y_axis_max_value[0] == null || y_axis_max_value[0] == '' || typeof(y_axis_max_value[0]) == 'undefined') ? 'auto' : y_axis_max_value[0];
        y_axis_obj.push({
            type:'value',
            name:y_axis_names[0],
            max:y_axis_d1_max_value,
            boundaryGap: [0, '100%']
        });
    }else if(y_axis_names.length == 2){
        if(groups_in_sec_y_axis == null || groups_in_sec_y_axis == '' || typeof(groups_in_sec_y_axis) == 'undefined' || groups_in_sec_y_axis.length == 0){
            alert('No feature based on second y-axis but two y-axis defined. Please check.');
            return;
        }
        var y_axis_d1_max_value = (y_axis_max_value == null || y_axis_max_value == '' || typeof(y_axis_max_value) == 'undefined' || y_axis_max_value.length < 1 || y_axis_max_value[0] == null || y_axis_max_value[0] == '' || typeof(y_axis_max_value[0]) == 'undefined') ? 'auto' : y_axis_max_value[0];
        y_axis_obj.push({
            type:'value',
            name:y_axis_names[0],
            max:y_axis_d1_max_value,
            boundaryGap: [0, '100%']
        });
        var y_axis_d2_max_value = (y_axis_max_value == null || y_axis_max_value == '' || typeof(y_axis_max_value) == 'undefined' || y_axis_max_value.length < 2 || y_axis_max_value[1] == null || y_axis_max_value[1] == '' || typeof(y_axis_max_value[1]) == 'undefined') ? 'auto' : y_axis_max_value[1];
        y_axis_obj.push({
            type:'value',
            name:y_axis_names[1],
            max:y_axis_d2_max_value,
            boundaryGap: [0, '100%']
        });
    }else{
        alert('You cannot choose y-axis more than two.');
        return;
    };


    //对需要分组的数据集装到不同的series中，并找出所有的分组
    data.forEach(function(r){
        if((new_series_map[r[data_group_column]] == null || new_series_map[r[data_group_column]] == '' || typeof(new_series_map[r[data_group_column]]) == 'undefined')){
            new_series_map[r[data_group_column]] = {};
            var new_series = new_series_map[r[data_group_column]];
            new_series.name = r[data_group_column];
            new_series.type = (r.type == null || r.type == '' || typeof(r.type) == 'undefined') ? 'line' : r.type;
            if(echartsUtil.is_in_arr(r[data_group_column], groups_in_sec_y_axis)){
                new_series.yAxisIndex = 1;
            }
            new_series.showSymbol = false;
            new_series.hoverAnimation = false;
            new_series.smooth = true;
            new_series.data = [];
            new_series.data.push({
                name:r[data_name_colume],
                value:[r[data_name_colume],r[data_value_column]]
            });
        }else{
            var new_series = new_series_map[r[data_group_column]];
            new_series.data.push({
                name:r[data_name_colume],
                value:[r[data_name_colume],r[data_value_column]]
            });
        }

        if(!echartsUtil.is_in_arr(r[data_group_column], legend_data)){
            legend_data.push(r[data_group_column]);
        }
    });

    //从得到的new_series_map中得到所有的series，加入到new_series_arr数组中
    for (var prop in new_series_map) {
        if (new_series_map.hasOwnProperty(prop)) {
            new_series_arr.push(new_series_map[prop]);
        }
    }
    //
    var option = {
        title: {
            text: (title == null || title == '' || typeof(title) == 'undefined')?'null named after for this plot.':title,
            subtext: (subtitle == null || subtitle == '' || typeof(subtitle) == 'undefined')?'null named after for this plot.':subtitle
        },
        tooltip: {
            trigger: 'axis',
            //formatter: echartsUtil.multi_tips_formatter,
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data:legend_data
        },
        toolbox: {
            feature: {
                saveAsImage: {},
                dataView:{}
            }
        },
        xAxis: {
            type:'time'
        },
        yAxis: y_axis_obj,
        series: new_series_arr
    };
    myChart.setOption(option);
};
echartsUtil.plot_time_series = function(element_id, title, max_value, series){
    var myChart = echarts.init(document.getElementById(element_id));
    var new_series = [];
    var legend_data = [];

    max_value = (max_value == null || max_value == '' || typeof(max_value) == 'undefined') ? 'auto' : max_value;


    series.forEach(function(r){
        new_series.push(
            {
                name: r.name,
                type: (r.type == null || r.type == '' || typeof(r.type) == 'undefined') ? 'line' : r.type,
                showSymbol: true,
                hoverAnimation: true,
                data: r.data
            }
        );
        legend_data.push(r.name);
    });


    var option = {
        title: {
            text: (title == null || title == '' || typeof(title) == 'undefined')?'null named after for this plot.':title
        },
        tooltip: {
            trigger: 'axis',
            formatter: echartsUtil.tips_formatter,
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data:legend_data
        },
        toolbox: {
            feature: {
                saveAsImage: {},
                dataView:{},
                dataZoom:{}
            }
        },
        xAxis: {
            type:'time'
        },
        yAxis: {
            type:'value',
            max:max_value,
            boundaryGap: [0, '100%']
        },
        series: new_series
    };
    myChart.setOption(option);
};


echartsUtil.plot_bar = function(element_id, title, subtitle, data, x_axis_col_name){
    var myChart = echarts.init(document.getElementById(element_id));

    var x_axis_arr = [];
    var x_axis = {};
    x_axis.type = 'category';
    x_axis.data = [];
    x_axis_arr[0] = x_axis;

    var y_series_map = {};
    var legend_data = [];
    var y_series = [];


    data.forEach(function(r){
        for(var col_name in r){
            if(col_name == x_axis_col_name){
                x_axis.data.push(r[x_axis_col_name]);
            }else{
                if(y_series_map[col_name] == null){
                    y_series_map[col_name] = {};
                    y_series_map[col_name].name = col_name;
                    y_series_map[col_name].type = 'bar';
                    y_series_map[col_name].data = [];
                    y_series_map[col_name].data.push(r[col_name]);
                    y_series_map[col_name].markLine = {
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    };
                    y_series_map[col_name].markPoint = {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    };
                }else{
                    y_series_map[col_name].data.push(r[col_name]);
                }
            }
        }
    });

    for(var col_name in data[0]){
        if(col_name == x_axis_col_name){
        }else{
            legend_data.push(col_name);
            y_series.push(y_series_map[col_name]);
        }
    };

    var option = {
        title : {
            text:title,
            subtext: (subtitle == null || subtitle == '' || typeof(subtitle) == 'undefined') ? '': subtitle
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:legend_data
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : x_axis,
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : y_series
    };
    myChart.setOption(option);
};


echartsUtil.plot_bar_or_line = function(element_id, title, subtitle, data, x_axis_col_name, plot_type, features_to_show,is_true){
    var myChart = echarts.init(document.getElementById(element_id));
    debugger
    var x_axis_arr = [];
    var x_axis = {};
    x_axis.type = 'category';
    x_axis.data = [];
    x_axis_arr[0] = x_axis;

    var y_series_map = {};
    var legend_data = [];
    var y_series = [];

    if(plot_type == null || plot_type == '' || typeof(plot_type) == 'undefined'){
        plot_type = 'line';
    }else if(plot_type != 'bar' && plot_type != 'line'){
        alert("Please confirm that plot type you choose is bar or line.");
        return;
    }

    data.forEach(function(r){
        for(var col_name in r){
            if(col_name == x_axis_col_name){
                x_axis.data.push(r[x_axis_col_name]);
            }else if(features_to_show.hasOwnProperty(col_name)){
                if(y_series_map[col_name] == null){
                    y_series_map[col_name] = {};
                    y_series_map[col_name].name = features_to_show[col_name];
                    y_series_map[col_name].type = plot_type;
                    y_series_map[col_name].data = [];
                    y_series_map[col_name].data.push((r[col_name] == null || r[col_name] == '' || typeof(r[col_name]) == 'undefined') ? 0 : r[col_name]);
                    y_series_map[col_name].markLine = {
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    };
                    y_series_map[col_name].markPoint = {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    };
                }else{
                    y_series_map[col_name].data.push((r[col_name] == null || r[col_name] == '' || typeof(r[col_name]) == 'undefined') ? 0 : r[col_name]);
                }
            }
        }
    });

    for(var col_name in data[0]){
        if(col_name == x_axis_col_name){
        }else if(features_to_show.hasOwnProperty(col_name)){
            legend_data.push(features_to_show[col_name]);
            y_series.push(y_series_map[col_name]);
        }
    };

    if(is_true){
        var option = {
            title : {
                text:title,
                subtext: (subtitle == null || subtitle == '' || typeof(subtitle) == 'undefined') ? '': subtitle
            },
            tooltip : {
                trigger: 'axis'
            },
            dataZoom: {
                show: true,
                start : 0
            },
            legend: {
                data:legend_data
            },
            toolbox: {
                show : true,
                feature : {
                    //dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : x_axis,
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : y_series
        };
    }else{
        var option = {
            title : {
                text:title,
                subtext: (subtitle == null || subtitle == '' || typeof(subtitle) == 'undefined') ? '': subtitle
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:legend_data
            },
            toolbox: {
                show : true,
                feature : {
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : x_axis,
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : y_series
        };
    }

    myChart.setOption(option);
};

/**
 * 环形图
 * @param element_id div对应id
 * @param title 环形图左上角标题
 * @param data  数据
 * @param plot_type pie圆环
 * name 数据库所查名称对应字段
 * value 数据所查名称对应的值
 */
echartsUtil.pie_stat = function(element_id, title, data, plot_type){
    var myChart = echarts.init(document.getElementById(element_id));
    var names = [];
    var sunResult = 0;
    var Max = 0;
    if(data != null && data.length > 0){
        if(null != data[0].value && "" != data[0].value && typeof(data[0].value) != 'undefined'){
            if(data[0].value > 0){
                Max = data[0].value;
            }
        }
        for(var i = 1;i<data.length;i++){
            if(null != data[i].value && "" != data[i].value && typeof(data[i].value) != 'undefined'){
                if(data[i].value > 0){
                    sunResult += data[i].value;
                    names.push(data[i].name);
                }else{
                    data.splice(i,1)
                }
            }
        }
        if((Max - sunResult) > 0){
            names.push("其他");
            data[0].name = "其他";
            data[0].value = Max - sunResult;
        }else{
            data.splice(0,1)
        }
    }
    if(plot_type == null || plot_type == '' || typeof(plot_type) == 'undefined'){
        plot_type = 'pie';
    }else if(plot_type != 'pie'){
        alert("Please confirm that plot type you choose is pie.");
        return;
    }
    var option = {
        title: {
            text: "\n"+title+"\n",
            x:'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:names
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                //dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'center',
                            max: 1548
                        }
                    }
                },
                //restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series: [
            {
                name:'数据占比',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        formatter: '{b}:({d}%)',
                        textStyle: {
                            fontSize: '20',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:data
            }
        ]
    };
    myChart.setOption(option);
};


/**
 * 饼状形图
 * @param element_id div对应id
 * @param title 饼状图左上角标题
 * @param data  数据
 * @param plot_type pie饼状
 * name 数据库所查名称对应字段
 * value 数据所查名称对应的值
 */
echartsUtil.plot_pie_stat = function(element_id, title, data, plot_type,items){
    var myChart = echarts.init(document.getElementById(element_id));
    var names = [];
    var sunResult = 0;
    var Max = 0;
    if(!data){
        return;
    }
    var resultData = data.data;
    if(!resultData){
        return;
    }
    for(var i = 0;i<resultData.length;i++){
        if(resultData[i][0]==null){
            resultData[i]=0;
        }
    }

    if(data != null && data.length > 0){
        if(null != data[0].value && "" != data[0].value && typeof(data[0].value) != 'undefined'){
            if(data[0].value > 0){
                Max = data[0].value;
            }
        }
        for(var i = 1;i<data.length;i++){
            if(null != data[i].value && "" != data[i].value && typeof(data[i].value) != 'undefined'){
                if(data[i].value > 0){
                    sunResult += data[i].value;
                    names.push(data[i].name);
                }else{
                    data.splice(i,1)
                }
            }
        }
        if((Max - sunResult) > 0){
            names.push("其他");
            data[0].name = "其他";
            data[0].value = Max - sunResult;
        }else{
            data.splice(0,1)
        }
    }
    if(plot_type == null || plot_type == '' || typeof(plot_type) == 'undefined'){
        plot_type = 'pie';
    }else if(plot_type != 'pie'){
        alert("Please confirm that plot type you choose is pie.");
        return;
    }
    var option = {
        title : {
            text: title,
            subtext:resultData[0],
            subtextStyle: { color: 'blue' },
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:items
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'类型',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:resultData[1]||0, name:items[0]},
                    {value:resultData[2]||0, name:items[1]}
                ]
            }
        ]
    };
    myChart.setOption(option);
};


/**
 * 柱状形图
 * @param element_id 对应id
 * @param title 柱状图左上角标题
 * @param data  数据
 * @param plot_type bar柱状
 * name 数据库所查名称对应字段
 * value 数据所查名称对应的值
 */
echartsUtil.plot_bar_stat = function(element_id, title, data, plot_type,items){
    var myChart = echarts.init(document.getElementById(element_id));
    var sunResult = 0;
    if(!data){
        return;
    }
    var resultData = data.data;
    if(!resultData){
        return;
    }
    var Max = 0;
    if(data != null && data.length > 0){
        if(null != data[0].value && "" != data[0].value && typeof(data[0].value) != 'undefined'){
            if(data[0].value > 0){
                Max = data[0].value;
            }
        }
        for(var i = 1;i<data.length;i++){
            if(null != data[i].value && "" != data[i].value && typeof(data[i].value) != 'undefined'){
                if(data[i].value > 0){
                    sunResult += data[i].value;
                    names.push(data[i].name);
                }else{
                    data.splice(i,1)
                }
            }
        }
        if((Max - sunResult) > 0){
            names.push("其他");
            data[0].name = "其他";
            data[0].value = Max - sunResult;
        }else{
            data.splice(0,1)
        }
    }
    if(plot_type == null || plot_type == '' || typeof(plot_type) == 'undefined'){
        plot_type = 'bar';
    }else if(plot_type != 'bar'){
        alert("Please confirm that plot type you choose is bar.");
        return;
    }
    var option = {
        title : {
            text: "\n"+title+"\n",
            x:'center'
        },
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            orient : 'horizontal',
            x : 'left',
            data:items
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'value'
            }
        ],
        yAxis : [
            {
                type : 'category',
                data : resultData[0],
                axisLabel : {
                    textStyle : {
                        color : 'black'
                    },
                    rotate: 10,
                    interval : 0,
                    formatter : function(params){
                        if(params){
                            var newParamsName = "";
                            var paramsNameNumber = params.length;
                            var provideNumber = 11;
                            var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                            if (paramsNameNumber > provideNumber) {
                                for (var p = 0; p < rowNumber; p++) {
                                    var tempStr = "";
                                    var start = p * provideNumber;
                                    var end = start + provideNumber;
                                    if (p == rowNumber - 1) {
                                        tempStr = params.substring(start, paramsNameNumber);
                                    } else {
                                        tempStr = params.substring(start, end) + "\n";
                                    }
                                    newParamsName += tempStr;
                                }

                            } else {
                                newParamsName = params;
                            }
                            return newParamsName
                        }
                    }

                }
            }
        ],
        series : [
            {
                name:items[0],
                type:'bar',
                stack: '',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:resultData[1]
            },
            {
                name:items[1],
                type:'bar',
                stack: '',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:resultData[2]
            }
        ]
    };
    myChart.setOption(option);
};

/**
 * 线形图
 * @param element_id 对应id
 * @param title 柱状图左上角标题
 * @param data  数据
 * @param plot_type line柱状
 * name 数据库所查名称对应字段
 * value 数据所查名称对应的值
 */
echartsUtil.plot_line_stat= function(element_id, title, data, plot_type,items){
    var myChart = echarts.init(document.getElementById(element_id));
    var names = [];
    var sunResult = 0;
    var Max = 0;
    if(!data){
        return;
    }
    var resultData = data.data;
    if(!resultData){
        return;
    }
    if(data != null && data.length > 0){
        if(null != data[0].value && "" != data[0].value && typeof(data[0].value) != 'undefined'){
            if(data[0].value > 0){
                Max = data[0].value;
            }
        }
        for(var i = 1;i<data.length;i++){
            if(null != data[i].value && "" != data[i].value && typeof(data[i].value) != 'undefined'){
                if(data[i].value > 0){
                    sunResult += data[i].value;
                    names.push(data[i].name);
                }else{
                    data.splice(i,1)
                }
            }
        }
        if((Max - sunResult) > 0){
            names.push("其他");
            data[0].name = "其他";
            data[0].value = Max - sunResult;
        }else{
            data.splice(0,1)
        }
    }
    if(plot_type == null || plot_type == '' || typeof(plot_type) == 'undefined'){
        plot_type = 'line';
    }else if(plot_type != 'line'){
        alert("Please confirm that plot type you choose is line.");
        return;
    }
    var option = {
        title : {
            text: "\n"+title+"\n",
            x:'center'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['金币账面流水','金币行为流水']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : resultData[0]
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value}'
                }
            }
        ],
        series : [
            {
                name:items[0],
                type:'line',
                data:resultData[1],
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:items[1],
                type:'line',
                data:resultData[2],
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name : '平均值'}
                    ]
                }
            }
        ]
    };
    myChart.setOption(option);
};

/**
 * 柱状形图
 * @param element_id 对应id
 * @param title 柱状图左上角标题
 * @param data  数据
 * @param plot_type bar柱状
 * name 数据库所查名称对应字段
 * value 数据所查名称对应的值
 */
echartsUtil.pie7 = function(element_id, title, data, plot_type){
    var myChart = echarts.init(document.getElementById(element_id));
    var items = "";
    forumConsole.ajaxCall('POST', contextPath+"/queryMatchScoreList.do", false, null, 'json', function(idata){
        items = idata;
    });
    var times = "";
    forumConsole.ajaxCall('POST', contextPath+"/queryMatchTimeList.do", false, null, 'json', function(mdata){
        times = mdata;
    });
    var sunResult = 0;
    if(!data){
        return;
    }
    var resultData = data.data;
    if(!resultData){
        return;
    }
    var Max = 0;
    if(data != null && data.length > 0){
        if(null != data[0].value && "" != data[0].value && typeof(data[0].value) != 'undefined'){
            if(data[0].value > 0){
                Max = data[0].value;
            }
        }
        for(var i = 1;i<data.length;i++){
            if(null != data[i].value && "" != data[i].value && typeof(data[i].value) != 'undefined'){
                if(data[i].value > 0){
                    sunResult += data[i].value;
                    names.push(data[i].name);
                }else{
                    data.splice(i,1)
                }
            }
        }
    }
    if(plot_type == null || plot_type == '' || typeof(plot_type) == 'undefined'){
        plot_type = 'pie';
    }else if(plot_type != 'pie'){
        alert("Please confirm that plot type you choose is pie.");
        return;
    }

    var idx = 1;
    var option = {
        timeline : {
            data : times,
            label : {
                formatter : function(s) {
                    return s.slice(0, 11);
                }
            }
        },
        options : [
            {
                title : {
                    text: "\n"+title+"\n",
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    data:items
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1700
                                }
                            }
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                series : [
                    {
                        name:'总进球（每场比赛总进球数）',
                        type:'pie',
                        center: ['50%', '45%'],
                        radius: '50%',
                        data:[
                            {value: idx * 128 + 80,  name:'Chrome'},
                            {value: idx * 64  + 160,  name:'Firefox'},
                            {value: idx * 32  + 320,  name:'Safari'},
                            {value: idx * 16  + 640,  name:'IE9+'},
                            {value: idx++ * 8  + 1280, name:'IE8-'}
                        ]
                    }
                ]
            },
            {
                series : [
                    {
                        name:'总进球（每场比赛总进球数）',
                        type:'pie',
                        data:[
                            {value: idx * 128 + 80,  name:'Chrome'},
                            {value: idx * 64  + 160,  name:'Firefox'},
                            {value: idx * 32  + 320,  name:'Safari'},
                            {value: idx * 16  + 640,  name:'IE9+'},
                            {value: idx++ * 8  + 1280, name:'IE8-'}
                        ]
                    }
                ]
            }
        ]
    };


};