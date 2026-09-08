import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts";

export default function ProgressionChart({ data }) {
  if (!data || data.length === 0) {
    return <p>Für diese Übung liegen noch keine Daten vor.</p>;
  }

  return (
    <ResponsiveContainer width="100%" height={300}>
      <LineChart data={data}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="date" />
        <YAxis unit="kg" />
        <Tooltip />
        <Line type="monotone" dataKey="poidsMax" stroke="#2563eb" strokeWidth={2} name="Max. Gewicht (kg)" />
      </LineChart>
    </ResponsiveContainer>
  );
}
