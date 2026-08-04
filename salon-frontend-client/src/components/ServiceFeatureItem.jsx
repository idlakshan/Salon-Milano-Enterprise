import { CheckCircle2 } from "lucide-react";

export const ServiceFeatureItem = ({ text }) => {
  return (
    <div className="flex items-center gap-3 text-sm text-white font-medium">
      <CheckCircle2 className="w-5 h-5 text-brand-red-light shrink-0" />
      <span>{text}</span>
    </div>
  );
};
