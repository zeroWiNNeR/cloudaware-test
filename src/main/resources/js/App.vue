<template>
  <v-app id="app">

    <v-row justify="center" no-gutters>
      <v-col cols="8" md="8">

        <v-card class="pa-1">
          <v-card-title>
            AWS Objects
          </v-card-title>

          <v-expansion-panels>
            <v-expansion-panel
                v-for="object in objects"
                :key="object.key"
                v-on:click="getObjectVersion(object.key)"
            >
              <v-expansion-panel-header>
                File: {{ object.key }}
                <v-spacer></v-spacer>
                Size (b): {{ object.size }}
              </v-expansion-panel-header>
              <v-expansion-panel-content>
                eTag: {{ object.eTag }} <br/>
                lastModified: {{ object.lastModified }} <br/>
                storageClass: {{ object.storageClass }} <br/>
                ownerId: {{ object.ownerId }} <br/>
                <br/>
                Versions: <br/>
                <div
                    v-for="version in getVersions"
                    :key="version.versionId"
                >
                  versionId: {{ version.versionId }} <br/>
                  eTag: {{ version.eTag }} <br/>
                  lastModified: {{ version.lastModified }} <br/>
                  size: {{ version.size }} <br/>
                  isLatest: {{ version.isLatest }} <br/>
                  storageClass: {{ version.storageClass }} <br/>
                  ownerId: {{ version.ownerId }} <br/>
                  <hr/>
                </div>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-card>

      </v-col>
    </v-row>

  </v-app>
</template>


<script>
export default {
  name: 'App',
  data() {
    return {
      objects: [],
      versions: [],
      headers: [
        {text: 'filename', align: 'start', sortable: false, value: 'key'},
        {text: 'size (b)', value: 'size'}
      ],
    }
  },
  created: function () {
    this.$http.get('/storage/objects').then(
        result => {
          result.json().then(data =>
              data.forEach(object => this.objects.push(object))
          )
        }, response => {
          alert("Error code: " + response.status + "\n" + response.bodyText)
        }
    )
  },
  methods: {
    getObjectVersion(key) {
      this.versions.splice(0, this.versions.length)
      this.$http.get('/storage/versions/' + key).then(
          result => {
            result.json().then(data =>
                data.forEach(object => this.versions.push(object))
            )
          }, response => {
            alert("Error code: " + response.status + "\n" + response.bodyText)
          }
      )
    }
  },
  computed: {
    getVersions() {
      return this.versions;
    }
  }
}
</script>


<style>

</style>
