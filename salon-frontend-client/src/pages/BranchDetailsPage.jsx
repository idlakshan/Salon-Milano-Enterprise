import { useState } from "react";
import BranchHeader from "../components/BranchHeader";
import ServiceCategorySidebar from "../components/ServiceCategorySidebar";
import ServiceCard from "../components/ui/ServiceCard";
import CartSummary from "../components/ui/CartSummary";
import {
  COLOMBO_BRANCH,
  SERVICE_CATEGORIES,
  SERVICES_DATA,
} from "../data/servicesData";

const BranchDetailsPage = () => {
  const [activeTab, setActiveTab] = useState("Services");
  const [selectedCategory, setSelectedCategory] = useState("haircut");
  const [cartItems, setCartItems] = useState([]);

  const addToCart = (service) => {
    if (!cartItems.find((item) => item.id === service.id)) {
      setCartItems([...cartItems, service]);
    }
  };

  const removeFromCart = (id) => {
    setCartItems(cartItems.filter((item) => item.id !== id));
  };

  const totalPrice = cartItems.reduce((sum, item) => sum + item.price, 0);

  const filteredServices = SERVICES_DATA.filter(
    (item) => item.category === selectedCategory,
  );

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

      {activeTab === "Services" ? (
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
          <ServiceCategorySidebar
            categories={SERVICE_CATEGORIES}
            selectedCategory={selectedCategory}
            onSelectCategory={setSelectedCategory}
          />

          <div className="lg:col-span-5 space-y-3">
            {filteredServices.map((service) => (
              <ServiceCard
                key={service.id}
                service={service}
                isAdded={cartItems.some((item) => item.id === service.id)}
                onAddToCart={addToCart}
              />
            ))}
          </div>

          <CartSummary
            cartItems={cartItems}
            onRemoveFromCart={removeFromCart}
            totalPrice={totalPrice}
          />
        </div>
      ) : (
        <div className="p-8 rounded-2xl bg-brand-dark-paper border border-brand-dark-border min-h-50">
          <h2 className="text-xl font-bold text-white">
            {activeTab} Section Content
          </h2>
        </div>
      )}
    </div>
  );
};

export default BranchDetailsPage;
