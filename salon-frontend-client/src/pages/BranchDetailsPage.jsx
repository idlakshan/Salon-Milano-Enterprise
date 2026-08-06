import { useState } from "react";
import BranchHeader from "../components/BranchHeader";

const COLOMBO_BRANCH = {
  id: "colombo-03",
  name: "Milano Salon Colombo 03",
  city: "Colombo 03",
  openTime: "09:00 AM",
  closeTime: "08:00 PM",
  rating: 4.8,
  totalReviews: 96,
  images: [
    "https://images.unsplash.com/photo-1633681926035-ec1ac984418a?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1521590832167-7bcbfaa6381f?q=80&w=600&auto=format&fit=crop",
    "https://images.unsplash.com/photo-1503951914875-452162b0f3f1?q=80&w=600&auto=format&fit=crop",
  ],
};

const BranchDetailsPage = () => {
  const [activeTab, setActiveTab] = useState("Services");

  return (
    <div className="bg-brand-dark-bg text-white min-h-screen pt-24 pb-16 px-4 sm:px-8 max-w-7xl mx-auto space-y-8">
      <BranchHeader branch={COLOMBO_BRANCH} />

      <div className="flex border-b border-brand-dark-border gap-4 sm:gap-8 text-xs sm:text-sm font-semibold">
        {["Services", "Reviews", "Create Review"].map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab)}
            className={`pb-3 transition-all cursor-pointer relative ${
              activeTab === tab
                ? "text-brand-red-light border-b-2 border-brand-red-light font-bold"
                : "text-brand-silver hover:text-white"
            }`}
          >
            {tab}
          </button>
        ))}
      </div>

      <div className="p-8 rounded-2xl bg-brand-dark-paper border border-brand-dark-border min-h-50">
        <h2 className="text-xl font-bold text-white">
          {activeTab} Section Content
        </h2>
      </div>
    </div>
  );
};

export default BranchDetailsPage;
