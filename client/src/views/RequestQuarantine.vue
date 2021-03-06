<template>
  <a-card style="max-width: 500px; margin: 2rem auto; min-height: 300px">
    <a-form
      :form="form"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 18 }"
      @submit.prevent="handleSubmit"
    > <!-- :colon="false" -->

      <a-form-item label="Patienten-ID"
                   v-if="this.givenPatientId">
        {{this.$route.params.patientFirstName}} {{this.$route.params.patientLastName}}
        ({{this.$route.params.patientId}})
      </a-form-item>
      <a-form-item label="Patienten-ID" v-else>
        <PatientInput
          v-decorator="['patientId',{ rules: [{
            required: true,
            message: 'Bitte geben Sie die Patienten-ID ein.'
          }]}]"
        />
      </a-form-item>

      <a-form-item label="Quarantäne bis">
        <DateInput
          :value = "today"
          v-decorator="['dateUntil', { rules: [{
            required: true,
            message: 'Bis wann soll der Patient in Quarantäne?',
          }]}]"
        />
      </a-form-item>

      <a-form-item label="Vorgemerkt am">
        <DateInput
          :defaultValue= 'today'
          v-decorator="['eventDate', { rules: [{
            required: false,
            message: 'Datum, für welches der Vermerk erfasst werden soll.',
          }]}]"
        />
      </a-form-item>

      <!-- Kommentar -->
      <a-form-item label="Kommentar">
        <a-textarea
          :autoSize="{ minRows: 3, maxRows: 5 }"
          placeholder="Kommentar hinzufügen"
          v-decorator="['comment']"
        />
      </a-form-item>

      <!-- Submit -->
      <a-divider />
      <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
        <a-button html-type="submit" type="primary">
          Speichern
        </a-button>
      </a-form-item>
    </a-form>

  </a-card>
</template>

<script lang="ts">
import { Patient } from '@/api/SwaggerApi'
import Api from '@/api'
import Vue from 'vue'
import DateInput from '../components/DateInput.vue'
import PatientInput from '../components/PatientInput.vue'
import moment from 'moment'

interface State {
  form: any; // eslint-disable-next-line
  patient?: Patient;
  today: moment.Moment;
}

export default Vue.extend({
  name: 'RequestQuarantine',
  components: {
    PatientInput,
    DateInput,
  },
  data(): State {
    return {
      form: this.$form.createForm(this),
      patient: undefined,
      today: moment(),
    }
  },
  computed: {
    givenPatientId(): string | undefined {
      return this.$route.params.patientId
    },
  },
  methods: {
    handleSubmit() {
      this.form.validateFields((err: Error, values: any) => { // eslint-disable-next-line
        if (err) {
          return
        }
        const request = {
          dateUntil: values.dateUntil.format('YYYY-MM-DD'),
          eventDate: values.eventDate ? values.eventDate.format('YYYY-MM-DD') : undefined,
          comment: values.comment,
        }
        const patientId = this.givenPatientId ? this.givenPatientId : values.patientId

        Api.requestQuarantineUsingPost(patientId, request).then(patient => {
          const h = this.$createElement
          this.$success({
            title: 'Der Quarantänevermerk wurde erfasst.',
            content: h('div', {}, [
              h('div', `Patient: ${patient.firstName} ${patient.lastName}`),
              h('div', `In Quarantäne bis: ${moment(patient.quarantineUntil).format('DD.MM.YYYY')}`),
            ]),
          })
          this.form.resetFields()
        }).catch(err => {
          const notification = {
            message: 'Fehler beim hinterlegen des Quarantänevermerks',
            description: err.message,
          }
          this.$notification.error(notification)
        })
      })
    },
  },
})
</script>

<style></style>
