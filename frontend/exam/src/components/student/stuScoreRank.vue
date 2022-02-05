<!--
描述：学生端，查询成绩并显示简单的相关统计信息。
作者：211027134 叶怀生
创建：2022年2月6日05:08:19
修改：2022年2月6日05:26:01
-->
<template>
  <div class="ScorePage">
    <div style="margin-bottom: 14px"></div>
    <el-container style="height: 680px;">
      <el-aside width="500px">
        <div style="margin-bottom: 64px"></div>
        <div style="margin: 0px 86px;">
          <el-progress type="dashboard" :percentage="(1-(rank/classTotalPerson))*100" :color="colors" :format="format"
                       width="320"
                       stroke-width="8"
          ></el-progress>
        </div>
        <el-descriptions title='' direction="vertical" :column="4" border>
          <el-descriptions-item label="姓名"><el-tag>叶怀生</el-tag></el-descriptions-item>
          <el-descriptions-item label="学号"><el-tag>211027134</el-tag></el-descriptions-item>
          <el-descriptions-item label="班级"><el-tag>2021级电子信息3班</el-tag></el-descriptions-item>
        </el-descriptions>
        <div style="margin-bottom: 12px"></div>
        <el-alert
          :title="tip"
          type="info"
          effect="dark"
          :closable="false"
        >
        </el-alert>
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
          <el-table-column prop="num" label="考试人数" width="100"></el-table-column>
          <el-table-column fixed="right" prop="rank" label="排名" width="100"></el-table-column>
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
  name: "stuScoreRank",
  data() {
    return{
      pagination: {
        records: [{
          name: '测试数据',
          end: '2022年2月5日15:03:48',
          description: '由小朋友制作的一份超级试卷大礼包',
          num: 100,
          rank: 41,
          examCode: 123
        }],
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      },
      terms: [{
        value: '1',
        label: '全部排名'
      }, {
        value: '2',
        label: '2021-2022-2'
      }],
      termValue: '1',
      rank: 41,
      classTotalPerson: 49,
      colors: [
        {color: '#F56C6C', percentage: 20},
        {color: '#E6A23C', percentage: 40},
        {color: '#67C23A', percentage: 60},
        {color: '#409EFF', percentage: 80},
        {color: '#ab66dd', percentage: 100}
      ],
      tip:'unkown'
    }
  },
  created() {
    this.tip='综合排名前'+(this.rank/this.classTotalPerson*100).toFixed(0)+'%，超越'+(this.classTotalPerson-this.rank)+'人！';
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
    format(percentage) {
      return percentage === 100 ? '满' : `前 ${(100-percentage).toFixed(0)}%`;
    }
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
