<!--
描述：学生端，查询成绩并显示简单的相关统计信息。
作者：211027134 叶怀生
创建：2022年2月6日05:08:19
修改：2022年2月6日05:26:56
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
          <el-table-column fixed="left" prop="name" label="考试名称" width="240"></el-table-column>
          <el-table-column prop="end" label="考试结束时间" width="200"></el-table-column>
          <el-table-column prop="description" label="考试描述" width="400"></el-table-column>
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
        records: [{
          name: '测试数据',
          end: '2022年2月5日15:03:48',
          description: '由小朋友制作的一份超级试卷大礼包',
          score: 100,
          state: '优秀',
          examCode: 123
        }],
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
        { value: 2, name: '优秀' },
        { value: 10, name: '良好' },
        { value: 32, name: '一般' },
        { value: 4, name: '及格' },
        { value: 2, name: '不及格' }
      ]
    }
  },
  methods: {
    getPageInfo() {
      // 分页查询考试信息
      this.$axios(
        `/api/answers/${this.pagination.current}/${this.pagination.size}`
      )
        .then(res => {
          this.pagination = res.data.data;
          console.log(res);
        })
        .catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getPageInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getPageInfo();
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "warning-row";
      } else {
        return "success-row";
      }
    },
    drawPie(id){
      this.charts = echarts.init(document.getElementById(id))
      this.charts.setOption({
        title: {
          text: '课程考试评级情况',
          subtext: '评级分布',
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
            color: [
              '#ab66dd',
              '#409EFF',
              '#67C23A',
              '#E6A23C',
              '#F56C6C'
            ]
          }
        ]
      })
    }
  },  //调用
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
