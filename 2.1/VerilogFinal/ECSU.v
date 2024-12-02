`timescale 1us / 1ps

module ECSU (
    input CLK,
    input RST,
    input thunderstorm,
    input [5:0] wind,
    input [1:0] visibility,
    input signed [7:0] temperature,
    input signed [7:0] distance1,
    input signed [7:0] jet_speed,
    output reg severe_weather,
    output reg emergency_landing_alert,
    output reg [1:0] ECSU_state,
    output reg target_approaching
);


parameter ALL_CLEAR = 2'b00;
parameter EMERGENCY = 2'b11;
parameter CAUTION = 2'b01;
parameter HIGH_ALERT = 2'b10;
reg [1:0] current_state, next_state;


always @(posedge CLK or posedge RST) begin
    if (RST) begin
        current_state <= ALL_CLEAR;
    end 
    else begin
        case (current_state)
            ALL_CLEAR: begin
                if ((wind >= 11 && wind <= 15) || visibility == 2'b01) begin
                    next_state <= CAUTION;
                end else if (thunderstorm || wind > 15 || temperature > 35 || temperature < -35 || visibility == 2'b11) begin
                    next_state <= HIGH_ALERT;
                end else begin
                    next_state <= ALL_CLEAR;
                end
            end
            
            CAUTION: begin
                if ((wind <= 10 && visibility == 2'b00)) begin
                    next_state <= ALL_CLEAR;
                end else if (thunderstorm || wind > 15 || temperature > 35 || temperature < -35 || visibility == 2'b11) begin
                    next_state <= HIGH_ALERT;
                end else begin
                    next_state <= CAUTION;
                end
            end
            
            HIGH_ALERT: begin
                if (temperature < -40 || temperature > 40 || wind > 20) begin
                    next_state <= EMERGENCY;
                end else if (!thunderstorm && wind <= 10 && temperature >= -35 && temperature <= 35 && visibility != 2'b11) begin
                    next_state <= CAUTION;
                end else begin
                    next_state <= HIGH_ALERT;
                end
            end
            
            EMERGENCY: begin
                // Emergency state is maintained until system reset
                next_state <= EMERGENCY;
            end
        endcase
    end
end

always @(posedge CLK or posedge RST) begin
    if (RST) begin
        severe_weather <= 0;
        emergency_landing_alert <= 0;
        ECSU_state <= ALL_CLEAR;
        target_approaching <= 0;
    end 
    else begin
        case (current_state)
            ALL_CLEAR: begin
                severe_weather <= 0;
                emergency_landing_alert <= 0;
            end
            
            CAUTION: begin
                severe_weather <= 0;
                emergency_landing_alert <= 0;
            end
            
            HIGH_ALERT: begin
                severe_weather <= 1;
                emergency_landing_alert <= 0;
            end
            
            EMERGENCY: begin
                severe_weather <= 1;
                emergency_landing_alert <= 1;
            end
        endcase
        
        current_state <= next_state;
        ECSU_state <= current_state;
        if (((distance1 + jet_speed * 100) - distance1) < 0) begin
            target_approaching <= 1;
        end else begin
            target_approaching <= 0;
        end
    end
end

endmodule
