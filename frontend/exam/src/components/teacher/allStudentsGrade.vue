<!--
描述：查看所有成绩
作者：211027134 叶怀生
时间：2022年1月30日15:50:32
修改：2022年2月6日05:25:49
-->
<template>
  <div class="all">
    <el-container style="height: 680px;">
      <el-aside width="500px">
        <div style="margin-bottom: 80px"></div>
        <div id="drawing" style="width: 500px;height: 400px;"></div>
      </el-aside>
      <el-main>
        <el-select v-model="classId" filterable placeholder="请选择班级后进行查询">
          <el-option
            v-for="item in classList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
        <el-tooltip class="item" effect="dark" content="查询指定班级的成绩" placement="top">
          <el-button icon="el-icon-search" circle style="margin-left: 4px;" @click="getClassScoreInfo(classId)"></el-button>
        </el-tooltip>
        <div style="margin-bottom: 14px"></div>
        <el-table :data="pagination.records" border>
          <el-table-column fixed="left" prop="tname" label="试卷名称" width="300"></el-table-column>
          <el-table-column prop="sno" label="学生学号" width="200"></el-table-column>
          <el-table-column prop="sname" label="学生姓名" width="200"></el-table-column>
          <el-table-column fixed="right" prop="score" label="学生成绩" width="200"></el-table-column>
        </el-table>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.current"
          :page-sizes="[6, 10]"
          :page-size="pagination.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
          class="page">
        </el-pagination>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import echarts from "echarts";

export default {
  data() {
    return {
      pagination: {
        //分页后的考试信息
        records: [],
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      form: {}, //保存点击以后当前考生的信息
      classList: [],
      classId: '',
      opinionData: [
        { value: 0, name: '优秀' },
        { value: 0, name: '良好' },
        { value: 0, name: '一般' },
        { value: 0, name: '及格' },
        { value: 0, name: '不及格' }
      ],
      Color: [],
      activeColor: [
        '#ab66dd',
        '#409EFF',
        '#67C23A',
        '#E6A23C',
        '#F56C6C'
      ],
      banColor: [
        '#cccccc',
        '#cccccc',
        '#cccccc',
        '#cccccc',
        '#cccccc'
      ]
    };
  },
  created() {
    this.checkClass(1); // [Wait Fix] 需要改成获取当前用户id，而不是固定id
  },//调用
  mounted(){
    this.Color = this.banColor;
    this.$nextTick(function() {
      this.drawPie('drawing')
    })
  },
  methods: {
    // 获取当前选定班级的成绩
    getClassScoreInfo(classId) {
      let submitData = {cid:classId,
        pageNum:this.pagination.current,
        pageSize:this.pagination.size};
      //分页查询指定班级成绩
      if(classId >= 0)
        this.$axios({
          url: 'http://8.130.16.20:8080/ShowScore',
          method: 'post',
          data: submitData
        }).then(res => {
          let resData = res.data.data.scores; // [WARNING] 有时候data是一个数组，而有时候却变成了又一个对象（结构体）……
          console.log(resData);
          if(resData != null && resData.length != 0){
            this.pagination.records = resData;
            for(let j = 0, len = resData.length; j < len; j++){
              let index = resData[j].score/10;
              if(index < 6)
                this.opinionData[4].value++;
              else
                this.opinionData[9-index].value++;
            }
            this.Color = this.activeColor;
            this.drawPie('drawing');
          }
          else{
            this.pagination.records = [];
            this.Color = this.banColor;
            for(let j = 0, len = this.opinionData.length; j < len; j++)
              this.opinionData[j].value = 0;
            this.drawPie('drawing');

          }

        }).catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getClassScoreInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getClassScoreInfo();
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
    // 查看指定教师所负责的所有班级
    checkClass(varAid){
      // 需要提交的数据
      let submitData = {aid:varAid,
        pageNum:1,
        pageSize:6};
      this.$axios({
        url:`http://8.130.16.20:8080/CheckClass`,
        method: 'post',
        data: submitData  // 注意，这里不要再套花括号了！
      }).then(res=>{
        let resData = res.data.data;
        if(resData != null && resData.length != 0)
          this.classList = resData;
      })
    },
    drawPie(id){
      this.charts = echarts.init(document.getElementById(id))
      this.charts.setOption({
        title: {
          text: '班级考试评级情况',
          subtext: '当前页所有试卷评级分布如下',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '该评级试卷数量',
            type: 'pie',
            radius: '50%',
            data: this.opinionData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            color: this.Color
          }
        ]
      })
    }
  }
};
</script>
<style lang="scss" scoped>
.all {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #dd5862 !important;
  }
}
.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
</style>
