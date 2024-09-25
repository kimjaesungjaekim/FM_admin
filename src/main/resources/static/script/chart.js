const canvas = document.querySelector('#myChart');  
const ctx = canvas.getContext('2d');  

// 새로운 그라데이션 정의
let gradient = ctx.createLinearGradient(0, 0, 0, 400); 
gradient.addColorStop(0, 'rgba(230, 250, 255, 0.8)'); 
gradient.addColorStop(0.5, 'rgba(25, 200, 247, 0.7)'); 

new Chart(canvas, { 
    type: 'bar', 
    data: {
        labels: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월'],  
        datasets: [
            {
                type: 'line', 
                label: '',
                data: [0, 2000, 3500, 4000, 3000, 4000, 6000, 8495],  
                borderWidth: 2,
                borderColor: '#19C8F7',  
                fill: false, 
                pointRadius: [0, 0, 0, 0, 0, 0, 0, 5], 
                tension: 0.4,
            },
            {
                type: 'bar', 
                label: '',
                data: [null, null, null, null, null, null, null, 10000], 
                backgroundColor: gradient, 
                borderWidth: 2,
                borderColor: '#19C8F7',
                yAxisID: 'y', 
                borderRadius: 10,  
            }
        ]
    },
    options: {
        responsive: false,
        maintainAspectRatio: false, 
        scales: {
            y: {
                beginAtZero: true, 
                max: 12000,
                grid: {
                    display: false  // y축 그리드 라인 숨기기
                }
            },
            x: {
                grid: {
                    display: false  // x축 그리드 라인 숨기기
                }
            }
        },
        plugins: {
            tooltip: {  // 호버 시 데이터 표시
                enabled: true,
                callbacks: {
                    label: function(context) {
                        if (context.dataset.type === 'line') {
                            return context.raw;  // 라인 그래프 값 표시
                        } 
                    }
                }
            },
            // annotation: {  
            //     annotations: {
            //         lastValue: {
            //             type: 'label',
            //             x: 7, 
            //             y: 8400, 
            //             color: '#333',
            //             content: ['8,495'],  
            //             font: {
            //                 size: 16,
            //                 weight: 'bold'
            //             },
            //             anchor: 'end',
            //             align: 'start',
            //         }
            //     }
            // }
        }
    }
});
