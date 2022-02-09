<!--
描述：教师端，查看所有教师授课班级的所有成绩
作者：211027134 叶怀生
时间：2022年1月30日15:50:32
修改：2022年2月10日01:48:25
-->
<template>
  <div class="all">
    <el-select v-model="classId" filterable placeholder="请选择班级后进行查询">
      <el-option
        v-for="item in classList"
        :key="item.id"
        :label="item.name"
        :value="item.id">
      </el-option>
    </el-select>
    <el-tooltip class="item" effect="dark" content="查询指定班级的成绩" placement="top">
      <el-button icon="el-icon-search" circle style="margin-left: 4px;" @click="getClassTestInfo(classId)"></el-button>
    </el-tooltip>
    <div style="margin-bottom: 14px"></div>
    <el-table :data="testPagination.records" border :row-class-name="tableRowClassName">
      <el-table-column fixed="left" prop="tname" label="考试名称" width="300"></el-table-column>
      <el-table-column prop="description" label="考试描述" width="640"></el-table-column>
      <el-table-column prop="starttime" label="开始时间" width="200"></el-table-column>
      <el-table-column prop="endtime" label="结束时间" width="200"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="entryTest(scope.row.cid, scope.row.id, scope.row.tname)" type="primary" size="small" icon="el-icon-more-outline">查看成绩</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleTestSizeChange"
      @current-change="handleTestCurrentChange"
      :current-page="testPagination.current"
      :page-sizes="[6, 10]"
      :page-size="testPagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="testPagination.total"
      class="page">
    </el-pagination>
    <el-dialog :title="scorePagination.currentCname" :visible.sync="dialogTableVisible" width="1100px">
      <el-container style="height: 680px;">
        <el-aside width="500px">
          <div style="margin-bottom: 80px"></div>
          <div id="drawing" style="width: 500px;height: 400px;"></div>
        </el-aside>
        <el-main width="500px">
          <div style="margin-bottom: 14px"></div>
          <el-table :data="scorePagination.records" border :row-class-name="tableRowClassName">
            <el-table-column fixed="left" prop="sname" label="学生姓名" width="200"></el-table-column>
            <el-table-column prop="sno" label="学生学号" width="200"></el-table-column>
            <el-table-column fixed="right" prop="score" label="学生成绩" width="100"></el-table-column>
          </el-table>
          <el-pagination
            @size-change="handleScoreSizeChange"
            @current-change="handleScoreCurrentChange"
            :current-page="scorePagination.current"
            :page-sizes="[6, 10]"
            :page-size="scorePagination.size"
            layout="total, sizes, prev, pager, next, jumper"
            :total="scorePagination.total"
            class="page">
          </el-pagination>
        </el-main>
      </el-container>
    </el-dialog>
  </div>
</template>

<script>
import echarts from "echarts";

export default {
  data() {
    return {
      testPagination: {
        //分页后的考试信息
        records: [],
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      scorePagination: {
        //分页后的考试信息
        records: [],
        currentCid: -1,
        currentCname: '',
        currentTid: -1,
        currentTname: '',
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      dialogTableVisible: false,  // 是否显示成绩Dialog
      form: {}, //保存点击以后当前考生的信息
      classList: [],
      classId: '',
      opinionData: [
        { value: 0, name: '优秀' },
        { value: 0, name: '良好' },
        { value: 0, name: '一般' },
        { value: 0, name: '及格' },
        { value: 0, name: '不及格' },
        { value: 0, name: '未参加考试' }
      ],
      Color: [],
      activeColor: [
        '#ab66dd',
        '#409EFF',
        '#67C23A',
        '#E6A23C',
        '#F56C6C',
        '#800000'
      ],
      banColor: [
        '#cccccc',
        '#cccccc',
        '#cccccc',
        '#cccccc',
        '#cccccc',
        '#cccccc'
      ],
      webSite: 'http://8.130.16.20:8080/' // 站点地址
    };
  },
  // Vue对象内置函数，在此函数处hook，可在页面绘制时执行特定函数
  created() {
    // 查询当前教师所管理的所有班级
    this.checkClass(1); // [Wait Fix] 需要改成获取当前用户id，而不是固定id
  },
  methods: {
    // 获取当前选定班级的所有考试
    getClassTestInfo(classId) {
      let submitData = {cid:classId};
      if(classId >= 0)
        this.$axios({
          url: this.webSite+'GetTests',
          method: 'post',
          data: submitData
        }).then(res => {
          let resData = res.data.data.tests;
          if(resData != null && resData.length != 0){
            this.testPagination.records = resData;
          }
          else{
            this.testPagination.records = [];
          }

        }).catch(error => {});
    },
    // 查看该次考试的成绩情况
    entryTest(classId, testId, testName){
      this.testPagination.current = 1;
      this.testPagination.size = 6;
      this.testPagination.currentCid = classId;
      this.testPagination.currentTid = testId;
      for(let j = 0, len = this.classList.length; j < len; j++){
        if(this.classList[j].id == this.classId){
          this.scorePagination.currentCname = this.classList[j].name;
          break;
        }
      }
      this.scorePagination.currentTname = testName;
      this.dialogTableVisible = true;
      /*
      // nexTick，下一次刷新dom时进行方法调用，暂时不需要。
      this.$nextTick(function() {
        this.drawPie('drawing')
      });
      */
      this.getClassScoreInfo();
      this.getClassScoreRank();
    },
    // 分析这次考试所有学生的评级情况
    getClassScoreRank() {
      let submitData = {  // [WARNING] 只用传tid，难道每个班的每次考试都是唯一的吗？
        tid: this.testPagination.currentTid
      };
      if(submitData.tid >= 0)
        this.$axios({
          url: this.webSite+'ShowRank',
          method: 'post',
          data: submitData
        }).then(res => {
          let rank = res.data.data.rank;
          this.opinionData[5].value = rank.sum;
          this.opinionData[0].value = rank.outstanding;
          this.opinionData[1].value = rank.excellent;
          this.opinionData[2].value = rank.good;
          this.opinionData[3].value = rank.pass;
          this.opinionData[4].value = rank.fail;
          for(let j = 0, len = this.opinionData.length; j < len-1; j++)
            this.opinionData[5].value -= this.opinionData[j].value;
          this.Color = this.activeColor;
          this.drawPie('drawing');
        }).catch(error => {});
    },
    // 分页查询指定班级的指定试卷成绩
    getClassScoreInfo() {
      let submitData = {
        cid: this.testPagination.currentCid,
        tid: this.testPagination.currentTid,
        pageNum: this.scorePagination.current,
        pageSize: this.scorePagination.size
      };
      if(submitData.cid >= 0 && submitData.tid >= 0)
        this.$axios({
          url: this.webSite+'ShowScore',
          method: 'post',
          data: submitData
        }).then(res => {
          let resData = res.data.data.scores;
          if(resData != null && resData.length != 0){
            this.scorePagination.records = resData;
          }
          else{
            this.scorePagination.records = [];
          }

        }).catch(error => {});
    },
    //改变当前记录条数
    handleTestSizeChange(val) {
      this.testPagination.size = val;
    },
    //改变当前页码，重新发送请求
    handleTestCurrentChange(val) {
      this.testPagination.current = val;
    },
    //改变当前记录条数
    handleScoreSizeChange(val) {
      this.scorePagination.size = val;
      this.getClassScoreInfo();
    },
    //改变当前页码，重新发送请求
    handleScoreCurrentChange(val) {
      this.scorePagination.current = val;
      this.getClassScoreInfo();
    },
    //关闭提醒
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
    // 查看指定教师所负责的所有班级
    checkClass(varAid){
      // 需要提交的数据
      // [WARNING] 这里使用的接口最好能提供一个非分页的接口
      let submitData = {aid:varAid,
        pageNum:1,
        pageSize:1000};
      this.$axios({
        url: this.webSite+'CheckClass',
        method: 'post',
        data: submitData  // 注意，这里不要再套花括号了！
      }).then(res=>{
        let resData = res.data.data.classes;
        if(resData != null && resData.length != 0)
          this.classList = resData;
      })
    },
    // Vue表格组件回调函数，使表格各行显示不同叠层样式
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "warning-row";
      } else {
        return "success-row";
      }
    },
    // echart插件绘制饼形图
    drawPie(id){
      this.charts = echarts.init(document.getElementById(id))
      this.charts.setOption({
        title: {
          text: '”'+this.scorePagination.currentTname+'” 成绩评级情况',
          subtext: '该班级在本次考试中所有评级分布如下',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'horizontal',
          left: 'center',
          top: 'bottom'
        },
        series: [
          {
            name: '该成绩评级数量',
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
