<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '500px' }"
    >
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-input v-model:value="param.name" placeholder="名称">
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="handleQuery({page: 1, size: pagination.pageSize})">
              查询
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()">
              新增
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <a-table
          :columns="columns"
          :data-source="ebooks"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template #cover="{ text: cover }">
          <img width="50" v-if="cover" :src="cover" alt="avatar"/>
        </template>
        <template v-slot:action="{ record }">
          <a-space size="small">

            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>

            <a-popconfirm
                title="删除后不可恢复，确认删除?"
                ok-text="是"
                cancel-text="否"
                @confirm="deleteBook(record.id)"
            >
              <a-button type="danger">
                删除
              </a-button>
            </a-popconfirm>
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
      axios.post("/ebook/save", ebook.value
      ).then((response) => {
        loading.value = false;
        modalLoading.value = false;
        modalVisible.value = false
        const data = response.data;
        // eslint-disable-next-line no-empty
        if (data) {
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
          // ebooks.value[editPos.value.positon] = ebook.value;
        }
      });
    }

    const deleteBook = (id: number) => {
      modalLoading.value = true;
      console.log("删除id为:" + id)
      axios.delete("/ebook/delete/" + id
      ).then((response) => {
        loading.value = false;
        modalLoading.value = false;
        modalVisible.value = false
        const data = response.data;
        // eslint-disable-next-line no-empty
        if (data) {
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
          // ebooks.value[editPos.value.positon] = ebook.value;
        }
      });
    }

    const add = () => {
      modalVisible.value = true;
      ebook.value = {};
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
    let editPos = ref({positon: 0})

    const edit = (record: any) => {
      modalVisible.value = true;
      ebook.value = record;
      console.log("ebook.value = record" + record.id)
      // editPos.value.positon = ebooks.value.indexOf(record)
    };


    onMounted(() => {
      handleQuery({
        page: 1,
        pageSize: pagination.value.pageSize
      })

    })

    // 查询新增按钮功能
    const param = ref({});


    return {
      param,
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,
      modalVisible,
      modalLoading,
      handleOk,
      edit,
      ebook,
      editPos,
      add,
      deleteBook
    };
  },
});
</script>


