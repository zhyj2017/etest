<!--
描述：学生端，查询成绩并显示简单的相关统计信息。
作者：211027134 叶怀生
创建：2022年2月6日05:08:19
修改：2022年2月10日00:47:04
-->
<template>
  <div class="ScorePage">
    <el-container style="height: 680px;">
      <el-aside width="500px">
        <div style="margin-bottom: 80px"></div>
        <div id="drawing" style="width: 500px;height: 400px;"></div>
      </el-aside>
      <el-main>
        <el-select v-model="termValue" placeholder="请选择">
          <el-option
            v-for="item in terms"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <div style="margin-bottom: 14px"></div>
        <el-table :data="pagination.records" border :row-class-name="tableRowClassName">
          <el-table-column fixed="left" prop="tname" label="考试名称" width="240"></el-table-column>
          <!--
          <el-table-column prop="end" label="考试结束时间" width="200"></el-table-column>
          <el-table-column prop="description" label="考试描述" width="400"></el-table-column>
          -->
          <el-table-column prop="end" label=" " width="600"></el-table-column>
          <el-table-column prop="score" label="得分" width="100"></el-table-column>
          <el-table-column fixed="right" prop="state" label="评级" width="100"></el-table-column>
        </el-table>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.current"
          :page-sizes="[6, 10]"
          :page-size="pagination.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
          class="page"
        ></el-pagination>
      </el-main>
    </el-container>
  </div>

</template>

<script>
import echarts from 'echarts'
export default {
  name: "stuScoreCheck",
  data() {
    return{
      pagination: {
        records: [],
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      },
      terms: [{
        value: '1',
        label: '全部成绩'
      }, {
        value: '2',
        label: '2021-2022-2'
      }],
      termValue: '1',
      opinionData:[
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
    }
  },
  // Vue对象内置函数，在此函数处hook，可在页面绘制时执行特定函数
  created(){
    this.Color = this.banColor;
    // 分页查询成绩信息，并获取所有成绩信息用于成绩分析
    this.getScoreInfo();
    this.getALLScoreInfo();
  },
  methods: {
    // 分页获取成绩信息
    getScoreInfo() {
      let submitData = {
        sid: 5, // [WARNING] 注意从cookie中获取
        pageNum: this.pagination.current,
        pageSize: this.pagination.size
      }
      // post分页请求
      this.$axios({
        url: this.webSite + 'Stu/ShowScore',
        method: 'post',
        data: submitData
      }).then(res => {
        if(res.data.code == '200'){
          this.pagination.records = res.data.data.scores;
          for(let j = 0, len = this.pagination.records.length; j < len; j++){
            let score = this.pagination.records[j].score;
            if(score < 60)
              this.pagination.records[j].state = '不及格';
            else if(60<= score < 70)
              this.pagination.records[j].state = '及格';
            else if(70<= score < 80)
              this.pagination.records[j].state = '一般';
            else if(80<= score < 90)
              this.pagination.records[j].state = '良好';
            else if(90< score)
              this.pagination.records[j].state = '优秀';
            else
              this.pagination.records[j].state = 'unkown';
          }
        }
        else{
          this.$message({
            message: '拉取成绩信息失败，' + res.data,
            type: 'warning'
          });
        }
      })
        .catch(error => {});
    },
    // 查询所有成绩，进行成绩分析
    getALLScoreInfo() {
      let submitData = {
        sid: 5, // [WARNING] 注意从cookie中获取
        pageNum: 1,
        pageSize: 1000
      }
      this.$axios({
        url: this.webSite + 'Stu/ShowScore',
        method: 'post',
        data: submitData
      }).then(res => {
        if(res.data.code == '200'){
          let records = res.data.data.scores;
          for(let j = 0, len = records.length; j < len; j++){
            let score = records[j].score;
            let index = parseInt(score/10);
            if(index < 6)
              this.opinionData[4].value++;
            else
              this.opinionData[Math.max(9-index,0)].value++;
          }
          this.Color = this.activeColor;
        }
        else{
          this.$message({
            message: '拉取所有成绩评级失败，' + res.data,
            type: 'warning'
          });
          this.Color = this.banColor;
        }
        this.drawPie('drawing');
      })
        .catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getScoreInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getScoreInfo();
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
          text: '课程考试评级情况',
          subtext: '评级分布如下',
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
            name: '该评级课程数量',
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
  },
  // Vue内置函数
  mounted(){
    this.$nextTick(function() {
      this.drawPie('drawing')
    })
  }
}
</script>

<style lang="scss" scoped>
  .ScorePage {
    padding: 0px 40px;
    .page {
      margin-top: 20px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }
</style>
