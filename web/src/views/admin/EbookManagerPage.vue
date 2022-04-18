<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '500px' }"
    >
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="ebooks"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template #cover="{ text: cover }">
          <img width="50" v-if="cover" :src="cover" alt="avatar"/>
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">

            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>

            <a-button type="danger">
              删除
            </a-button>
          </a-space>
        </template>
      </a-table>

    </a-layout-content>
  </a-layout>

  <a-modal
      title="电子书表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleOk"
  >
    <a-form :model="ebook" :label-col="{ span: 6 }">
      <a-form-item label="封面">
        <a-input v-model:value="ebook.cover"/>
      </a-form-item>
      <a-form-item label="名称">
        <a-input v-model:value="ebook.name"/>
      </a-form-item>
      <a-form-item label="分类1">
        <a-input v-model:value="ebook.description"/>
      </a-form-item>
      <a-form-item label="描述">
        <a-input v-model:value="ebook.description" type="textarea"/>
      </a-form-item>
    </a-form>

  </a-modal>

</template>


<script lang="ts">
import {SmileOutlined, DownOutlined} from '@ant-design/icons-vue';
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';

export default defineComponent({
  name: "AdminEbook",
  components: {
    SmileOutlined,
    DownOutlined,
  },
  setup() {
    const ebooks = ref();
    const pagination = ref({
      current: 1,
      pageSize: 4,
      total: 0
    });
    const loading = ref(false);
    const columns = [
      {
        title: '封面',
        dataIndex: 'cover',
        slots: {customRender: 'cover'}
      },
      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '分类一',
        key: 'category1Id',
      },
      {
        title: '文档数',
        dataIndex: 'docCount'
      },
      {
        title: '阅读数',
        dataIndex: 'viewCount'
      },
      {
        title: '点赞数',
        dataIndex: 'voteCount'
      },
      {
        title: 'Action',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];

    /**
     * 编辑框 属性
     */
    const modalVisible = ref(false)
    const modalLoading = ref(false)
    const handleOk = () => {
      modalLoading.value = true;
      setTimeout(() => {
        modalLoading.value = false;
        modalVisible.value = false;
      }, 2000)
    }

    /**
     * 数据查询
     **/
    const handleQuery = (params: any) => {
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      ebooks.value = [];
      axios.get("/ebook/bookLists", {
            params: {
              page: params.page,
              pageSize: params.pageSize
            }
          }
      ).then((response) => {
        loading.value = false;
        const data = response.data;
        ebooks.value = data.data.pageLists;

        // 重置分页按钮
        pagination.value.current = params.page;
        pagination.value.total = data.data.total;
      });
    };

    const handleTableChange = (pagination: any) => {
      handleQuery({
        page: pagination.current,
        pageSize: pagination.pageSize,
      })
    };


    const ebook = ref({})

    const edit = (record: any) => {
      modalVisible.value = true;
      ebook.value = record;
    };


    onMounted(() => {
      handleQuery({
        page: 1,
        pageSize: pagination.value.pageSize
      })

    })


    return {
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,
      modalVisible,
      modalLoading,
      handleOk,
      edit,
      ebook
    };
  },
});
</script>


