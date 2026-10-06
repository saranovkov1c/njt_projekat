export class Response {
  timestamp!: Date;
  statusCode!: Number;
  status!: string;
  reason!: string;
  message!: string;
  data!: { values: Object[]; value: Object };
}
