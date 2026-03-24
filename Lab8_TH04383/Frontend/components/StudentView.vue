<template>
  <div class="container">
    <h2>Student Management</h2>
    <div class="mb-3">
      <p>id: <input class="form-control" v-model="form.id" /></p>
      <p>Name: <input class="form-control" v-model="form.name" /></p>
      <p>Gender:</p>
      <div class="form-check form-check-inline">
        <input
          class="form-check-input"
          v-model="form.gender"
          type="radio"
          value="true"
        />
        Male
      </div>
      <div class="form-check form-check-inline">
        <input
          class="form-check-input"
          v-model="form.gender"
          type="radio"
          value="false"
        />
        Female
      </div>
      <p>Mark: <input class="form-control" v-model="form.mark" /></p>
      <div class="btn-group mb-3">
        <button class="btn btn-primary" @click="ctrl.create">Create</button>
        <button class="btn btn-warning" @click="ctrl.update">Update</button>
        <button class="btn btn-danger" @click="ctrl.deleteFromForm">
          Delete
        </button>
        <button class="btn btn-secondary" @click="ctrl.reset">Reset</button>
      </div>
    </div>
    <table class="table table-bordered table-striped">
      <thead class="table-dark">
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Gender</th>
          <th>Mark</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="e in list" :key="e.id">
          <td>{{ e.id }}</td>
          <td>{{ e.name }}</td>
          <td>{{ e.gender ? "Male" : "Female" }}</td>
          <td>{{ e.mark }}</td>
          <td>
            <button
              class="btn btn-sm btn-outline-primary me-2"
              @click="ctrl.edit(e)"
            >
              Edit
            </button>
            <button
              class="btn btn-sm btn-outline-danger"
              @click="ctrl.remove(e)"
            >
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script setup>
import { ref } from "vue";
import axios from "axios";
const form = ref({});
const list = ref([]);
const host = "http://localhost:8080";
const ctrl = {
  init() {
    this.load();
    this.reset();
  },
  reset() {
    form.value = { id: "", name: "", gender: true, mark: 5 };
  },
  load() {
    var url = `${host}/students`;
    axios
      .get(url)
      .then((resp) => {
        list.value = resp.data;
      })
      .catch((error) => {
        console.error("Error loading:", error);
      });
  },
  edit(entity) {
    var url = `${host}/students/${entity.id}`;
    axios
      .get(url)
      .then((resp) => {
        form.value = resp.data;
        form.value.gender = form.value.gender.toString();
      })
      .catch((error) => {
        console.error("Error editing:", error);
      });
  },
  create() {
    var entity = Object.assign({}, form.value);
    entity.gender = entity.gender === "true";
    entity.mark = parseFloat(entity.mark);
    var url = `${host}/students`;
    axios
      .post(url, entity)
      .then((resp) => {
        this.load();
        this.reset();
      })
      .catch((error) => {
        console.error("Error creating:", error);
      });
  },
  update() {
    var entity = Object.assign({}, form.value);
    entity.gender = entity.gender === "true";
    entity.mark = parseFloat(entity.mark);
    var url = `${host}/students/${entity.id}`;
    axios
      .put(url, entity)
      .then((resp) => {
        this.load();
      })
      .catch((error) => {
        console.error("Error updating:", error);
      });
  },
  deleteFromForm() {
    if (!form.value.id) {
      alert("Please select a student to delete from the list first");
      return;
    }
    var url = `${host}/students/${form.value.id}`;
    axios
      .delete(url)
      .then((resp) => {
        this.load();
        this.reset();
      })
      .catch((error) => {
        console.error("Error deleting from form:", error);
      });
  },
  remove(entity) {
    if (confirm(`Are you sure you want to delete ${entity.name}?`)) {
      var url = `${host}/students/${entity.id}`;
      axios
        .delete(url)
        .then((resp) => {
          this.load();
          this.reset();
        })
        .catch((error) => {
          console.error("Error removing:", error);
        });
    }
  },
};
ctrl.init();
</script>

<style scoped>
.container {
  margin-top: 20px;
}
.btn {
  margin-right: 5px;
}
</style>
