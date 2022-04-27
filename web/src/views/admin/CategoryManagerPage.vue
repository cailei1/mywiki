<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '500px' }"
    >
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-button type="primary" @click="handleQuery()">
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
          v-if="level1.length > 0"
          :columns="columns"
          :data-source="level1"
          :pagination="false"
          :row-key="record => record.id"
          :loading="loading"
      >

        <template v-slot:action="{ record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>
            <a-popconfirm
                title="删除后不可恢复，确认删除?"
                ok-text="是"
                cancel-text="否"
                @confirm="deletCategory(record.id)"
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
      title="分类表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleOk"
  >
    <a-form :model="category" :label-col="{ span: 6 }">
      <a-form-item label="名称">
        <a-input v-model:value="category.name"/>
      </a-form-item>
      <a-form-item label="父分类">
        <a-select
            v-model:value="category.parent"
            ref="select"
        >
          <a-select-option :value="0">
            无
          </a-select-option>
          <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disabled="category.id === c.id">
            {{ c.name }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="排序">
        <a-input v-model:value="category.sort" type="textarea"/>
      </a-form-item>
    </a-form>

  </a-modal>

</template>


<script lang="ts">
import {SmileOutlined, DownOutlined} from '@ant-design/icons-vue';
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from 'ant-design-vue'
import {Tool} from "@/utils/Tool";

export default defineComponent({
  name: "AdminEbook",
  components: {
    SmileOutlined,
    DownOutlined,
  },
  setup() {
    const categorys = ref();
    const loading = ref(false);
    const columns = [

      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '父类',
        dataIndex: 'parent',
      },
      {
        title: '排序',
        dataIndex: 'sort'
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
      axios.post("/category/save", category.value
      ).then((response) => {
        loading.value = false;
        modalLoading.value = false;
        modalVisible.value = false
        const data = response.data;

        console.log("data code:" + data.code)
        // eslint-disable-next-line no-empty
        if (data.code == 200) {
          handleQuery();
          // categorys.value[editPos.value.positon] = category.value;
        } else {
          message.error(data.msg);
        }
      });
    }

    const deletCategory = (id: number) => {
      modalLoading.value = true;
      console.log("删除id为:" + id)
      axios.delete("/category/delete/" + id
      ).then((response) => {
        loading.value = false;
        modalLoading.value = false;
        modalVisible.value = false
        const data = response.data;
        // eslint-disable-next-line no-empty
        if (data.code == 200) {
          handleQuery();
          // categorys.value[editPos.value.positon] = category.value;
        }
      });
    }

    const add = () => {
      modalVisible.value = true;
      category.value = {};
    }


    // const handleTableChange = () => {
    //   handleQuery()
    // };


    const category = ref({})
    let editPos = ref({positon: 0})

    const edit = (record: any) => {
      modalVisible.value = true;
      category.value = Tool.copy(record);
      console.log("category.value = record" + record.id)
      // editPos.value.positon = categorys.value.indexOf(record)
    };


    /**
     * 一级分类树，children属性就是二级分类
     * [{
     *   id: "",
     *   name: "",
     *   children: [{
     *     id: "",
     *     name: "",
     *   }]
     * }]
     */
    const level1 = ref(); // 一级分类树，children属性就是二级分类
    level1.value = [];


    /**
     * 数据查询
     **/
    const handleQuery = () => {
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      level1.value = [];
      axios.get("/category/categories"
      ).then((response) => {
        loading.value = false;
        const data = response.data;

        if (data.code == 200) {
          categorys.value = data.data;
          level1.value = [];
          level1.value = Tool.array2Tree(categorys.value, 0);
          console.log("树形结构：", level1);
        } else {
          message.error(data.msg)
        }
      });
    };


    onMounted(() => {
      handleQuery()

    })

    // 查询新增按钮功能
    const param = ref();
    param.value = {};


    return {
      param,
      categorys,
      columns,
      loading,
      modalVisible,
      modalLoading,
      handleOk,
      edit,
      category,
      editPos,
      add,
      deletCategory,
      handleQuery,
      level1
    };
  },
});
</script>


