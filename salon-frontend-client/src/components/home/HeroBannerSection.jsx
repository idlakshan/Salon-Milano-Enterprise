import { useState } from "react";
import { Search, Sparkles } from "lucide-react";

const HeroBannerSection = () => {
  const [query, setQuery] = useState("");
  const [focused, setFocused] = useState(false);

  return (
    <section className="relative w-full h-[80vh] max-h-187.5 min-h-130 overflow-hidden flex flex-col justify-between pt-20 pb-8">
      <div className="absolute inset-0 w-full h-full z-0 overflow-hidden">
        <div
          className="w-full h-full bg-cover bg-center transition-transform duration-1000 scale-105"
          style={{
            backgroundImage:
              "url('https://images.unsplash.com/photo-1560066984-138dadb4c035?q=80&w=1800&auto=format&fit=crop')",
          }}
        />
        <div className="absolute inset-0 bg-linear-to-t from-brand-dark-bg via-brand-dark-bg/40 to-brand-dark-bg/80" />
      </div>

      <div className="w-full max-w-2xl mx-auto px-6 z-20 pt-8 sm:pt-14">
        <div
          className={`rounded-2xl p-1.5 transition-all duration-300 backdrop-blur-xl ${
            focused
              ? "border border-brand-red-light/60 bg-brand-dark-paper/90"
              : "border border-brand-dark-border bg-brand-dark-paper/75 hover:border-brand-silver-border"
          }`}
        >
          <div className="flex items-center gap-2">
            <Search
              className={`w-5 h-5 ml-3 shrink-0 transition-colors ${
                focused ? "text-brand-red-light" : "text-brand-silver-dark"
              }`}
            />

            <input
              type="text"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              onFocus={() => setFocused(true)}
              onBlur={() => setFocused(false)}
              placeholder="Search a service"
              className="flex-1 bg-transparent outline-none text-sm sm:text-base py-2.5 text-white placeholder-brand-silver-dark"
            />

            <button className="shrink-0 font-semibold text-sm px-6 py-2.5 rounded-xl bg-brand-red hover:bg-brand-red-hover text-white transition-all shadow-md active:scale-95 cursor-pointer">
              Search
            </button>
          </div>
        </div>
      </div>

      <div className="z-10 max-w-[1580px]  w-full mx-auto px-6 sm:px-12 text-left mb-2 sm:mb-4">
        <div className="max-w-xl">
          <div className="fade-in-1 inline-flex items-center gap-2 px-3 py-1 rounded-full bg-brand-dark-sidebar/60 border border-brand-dark-border backdrop-blur-md mb-3">
            <Sparkles className="w-3.5 h-3.5 text-brand-red-light" />
            <span className="text-[11px] uppercase tracking-widest text-brand-silver font-semibold">
              Milano Salon Enterprise Sri Lanka
            </span>
          </div>

          <h1 className="fade-in-2 font-display text-3xl sm:text-5xl lg:text-6xl font-extrabold leading-[1.08] text-white tracking-tight">
            Look sharp. <br />
            Feel <em className="text-brand-red-light not-italic">sharper</em>.
          </h1>

          <p className="fade-in-3 mt-3 text-xs sm:text-sm md:text-base text-brand-silver leading-relaxed max-w-md">
            Where Expert Care Meets Luxurious Services for a Transformative
            Beauty Experience in Sri Lanka.
          </p>
        </div>
      </div>
    </section>
  );
}

export default HeroBannerSection;